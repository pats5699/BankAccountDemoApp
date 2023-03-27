package com.quickplay.bank_app.ui.viewmodels

import androidx.lifecycle.*
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.repository.account.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountListViewModel @Inject constructor(private val accountRepository: AccountRepository) :
    ViewModel() {

    private val accounts: LiveData<List<Account>> = accountRepository.getAllAccounts()
    val accountsByType = MediatorLiveData<Map<String, List<Account>>>()
    val iHaveAmount = MediatorLiveData<Double>()
    val iOweAmount = MediatorLiveData<Double>()


    init {
        accountsByType.addSource(accounts) { list ->
            val map: Map<String, List<Account>> = list.groupBy { account -> account.type.name }
            accountsByType.value = map
        }
        iOweAmount.addSource(accounts) { list ->
            var total = 0.0
            list.forEach { account ->
                val amount = account.balance.toDouble()
                if (amount < 0) {
                    total += amount
                }
            }

            iOweAmount.value = -total
        }
        iHaveAmount.addSource(accounts) { list ->
            var total = 0.0
            list.forEach { account ->
                val amount = account.balance.toDouble()
                if (amount > 0) {
                    total += amount
                }
            }
            iHaveAmount.value = total
        }
    }

    private val _loadingStatus = MutableLiveData<Resource>()
    val loadingStatus: LiveData<Resource>
        get() = _loadingStatus

    fun fetchFromNetwork() {

        _loadingStatus.value = Resource.Loading
        viewModelScope.launch {
            accountRepository.deleteAccounts()
            _loadingStatus.value = accountRepository.fetchAccounts()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            accountRepository.deleteAccounts()
        }
        fetchFromNetwork()
    }

}