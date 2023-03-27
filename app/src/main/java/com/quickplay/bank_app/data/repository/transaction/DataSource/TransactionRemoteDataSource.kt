package com.quickplay.bank_app.data.repository.transaction.DataSource

import com.quickplay.bank_app.data.Response
import com.quickplay.bank_app.data.api.RBCService
import com.quickplay.bank_app.data.model.Transaction

class TransactionRemoteDataSource(private val rbcService: RBCService) {

    suspend fun getTransactionsFromAPI(accountNumber: String): Response<List<Transaction>> {
        return rbcService.getTransactions(accountNumber)
    }

    suspend fun getAdditionalCreditCardTransactions(accountNumber: String): Response<List<Transaction>> {
        return rbcService.getAdditionalCreditCardTransactions(accountNumber)
    }
}