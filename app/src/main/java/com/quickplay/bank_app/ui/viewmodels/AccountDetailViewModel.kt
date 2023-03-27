package com.quickplay.bank_app.ui.viewmodels

import androidx.lifecycle.*
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.getDate
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.model.AccountType
import com.quickplay.bank_app.data.model.Transaction
import com.quickplay.bank_app.data.repository.transaction.TransactionRepository
import com.quickplay.bank_app.di.ViewModelFactories
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class AccountDetailViewModel @AssistedInject constructor(
    @Assisted val account: Account,
    private val transactionRepository: TransactionRepository
) :
    ViewModel() {

    /**
     *  Livedata of list of transactions, fetch from the database.
     */
    private val transactionsLiveData: LiveData<List<Transaction>> =
        transactionRepository.getAllTransactions(account.number)

    /**
     *  MediatorLiveData of Map. Here Key is date and value is the list of transaction with same date.
     */
    val transactionByTime: MediatorLiveData<Map<String, List<Transaction>>> = MediatorLiveData()

    init {
        transactionByTime.addSource(transactionsLiveData) { list ->
            val transactions = list.groupBy { transition -> getDate(transition.date) }
            transactions.values.forEach { list ->
                list.sortedBy { transaction -> transaction.date }
            }
            transactionByTime.value = transactions
        }
    }


    /**
     * List of Statuss to show for API Call's status.
     * one for all transaction and other is additional transactions.
     */
    private val _loadingStatus = MutableLiveData<List<Resource>>()
    val loadingStatus: LiveData<List<Resource>>
        get() = _loadingStatus

    /**
     * Fetch transactions from the server and change [loadingStatus] accordingly
     */
    fun fetchFromNetwork() {
        val list = mutableListOf(Resource.Loading)
        if (account.type == AccountType.CREDIT_CARD) {
            list.add(Resource.Loading)
        }
        _loadingStatus.value = list
        viewModelScope.launch {
            transactionRepository.deleteTransactions(accountNumber = account.number)
            _loadingStatus.value = transactionRepository.fetchAllTransactions(
                account.number,
                account.type == AccountType.CREDIT_CARD
            )
        }
    }

    /**
     * Delete old data from the database and fetch new data from the API.
     */
    fun refreshData() {
        viewModelScope.launch {
            transactionRepository.deleteTransactions(account.number)
        }
        fetchFromNetwork()
    }

    class Factory(
        private val assistedFactory: ViewModelFactories.AccountDetailViewModelAssistedFactory,
        private val account: Account
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return assistedFactory.create(account) as T
        }
    }

}


