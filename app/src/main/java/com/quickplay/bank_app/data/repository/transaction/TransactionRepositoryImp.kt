package com.quickplay.bank_app.data.repository.transaction

import android.util.Log
import androidx.lifecycle.LiveData
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.Response
import com.quickplay.bank_app.data.model.Transaction
import com.quickplay.bank_app.data.repository.transaction.DataSource.TransactionLocalDataSource
import com.quickplay.bank_app.data.repository.transaction.DataSource.TransactionRemoteDataSource
import com.quickplay.bank_app.data.repository.transaction.TransactionRepository.Companion.ADDITIONAL_TRANSLATION
import com.quickplay.bank_app.data.repository.transaction.TransactionRepository.Companion.ALL_TRANSLATION
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


class TransactionRepositoryImp(
    private val transactionLocalDataSource: TransactionLocalDataSource,
    private val transactionRemoteDataSource: TransactionRemoteDataSource
) : TransactionRepository {

    override fun getAllTransactions(accountNumber: String): LiveData<List<Transaction>> {
        return transactionLocalDataSource.getAllTransactionFromDb(accountNumber)
    }

    override suspend fun fetchAllTransactions(
        accountNumber: String,
        isCreditAccount: Boolean
    ): List<Resource> = withContext(Dispatchers.IO) {
        val deferredTransactionRemoteDataSource: Deferred<Response<List<Transaction>>> =
            async {
                transactionRemoteDataSource.getTransactionsFromAPI(accountNumber)
            }


        var deferredCreditTransaction: Deferred<Response<List<Transaction>>>? =
            if (isCreditAccount) {
                async {
                    transactionRemoteDataSource.getAdditionalCreditCardTransactions(accountNumber)
                }
            } else {
                null
            }


        val transactionRemoteDataSource = deferredTransactionRemoteDataSource.await()
        val creditTransactionRemote = deferredCreditTransaction?.await()


        val resources: MutableList<Resource> = mutableListOf()
        val transactions = mutableListOf<Transaction>()
        if (transactionRemoteDataSource.isSuccessful) {
            transactions.addAll(transactionRemoteDataSource.body!!)
            resources.add(ALL_TRANSLATION, Resource.Success)
        } else {
            resources.add(
                ALL_TRANSLATION,
                Resource.Error
            )
        }

        creditTransactionRemote?.let {
            if (it.isSuccessful) {
                transactions.addAll(it.body!!)
                resources.add(ADDITIONAL_TRANSLATION, Resource.Success)
            } else {
                resources.add(
                    ADDITIONAL_TRANSLATION,
                    Resource.Error
                )
            }
        }

        transactionLocalDataSource.saveTransactionsToDB(transactions)

        resources
    }


    override suspend fun deleteTransactions(accountNumber: String) = withContext(Dispatchers.IO) {
        transactionLocalDataSource.deleteAllTransactionsFromDb(accountNumber)
    }


}