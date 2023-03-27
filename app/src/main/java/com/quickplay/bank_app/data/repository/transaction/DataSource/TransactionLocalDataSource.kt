package com.quickplay.bank_app.data.repository.transaction.DataSource

import androidx.lifecycle.LiveData
import com.quickplay.bank_app.data.db.TransactionDao
import com.quickplay.bank_app.data.model.Transaction

class TransactionLocalDataSource(private val transactionDao: TransactionDao) {
    fun getAllTransactionFromDb(accountNumber: String): LiveData<List<Transaction>> {
        return transactionDao.getAllTransactions(accountNumber)
    }

    suspend fun saveTransactionsToDB(transactions: List<Transaction>) {
        transactionDao.saveTransactions(transactions)
    }

    suspend fun deleteAllTransactionsFromDb(accountNumber: String) {
        transactionDao.deleteAllTransactions(accountNumber)
    }
}