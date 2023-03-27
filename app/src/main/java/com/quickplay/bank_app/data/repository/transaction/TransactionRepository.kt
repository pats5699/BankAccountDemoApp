package com.quickplay.bank_app.data.repository.transaction

import androidx.lifecycle.LiveData
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.model.Transaction

interface TransactionRepository {

    companion object {
        /**
         * Constant for getting all_transaction's status.
         * */
        const val ALL_TRANSLATION = 0

        /**
         * Constant for getting Additional_Transaction's status.
         * */
        const val ADDITIONAL_TRANSLATION = 1
    }

    /**
     * Get all transaction from the database associate with specific account number.
     * @param accountNumber for getting it's transactions.
     * @return list of transaction
     */
    fun getAllTransactions(accountNumber: String): LiveData<List<Transaction>>

    /**
     * Fetch transaction, including additional, from the server and store inside the database.
     * @param accountNumber  for getting it's transactions.
     * @param isCreditAccount true if the account type is [CREDIT_CARD] else false.
     * (require for fetching additional data.)
     */
    suspend fun fetchAllTransactions(
        accountNumber: String,
        isCreditAccount: Boolean
    ): List<Resource>


    /**
     * Delete all Transactions from the data base
     * */
    suspend fun deleteTransactions(accountNumber: String)
}