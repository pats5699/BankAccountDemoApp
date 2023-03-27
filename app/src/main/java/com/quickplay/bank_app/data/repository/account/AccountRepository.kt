package com.quickplay.bank_app.data.repository.account

import androidx.lifecycle.LiveData
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.model.Account


interface AccountRepository {
    /**
     * @return return list of accounts from the room database
     * */
    fun getAllAccounts(): LiveData<List<Account>>

    /**
     * Fetch Accounts from the API and store inside the room database
     * @return return [Resource] status whether the data was fetched successfully or not.
     * */
    suspend fun fetchAccounts(): Resource

    /**
     * Delete all accounts from the data base
     * */
    suspend fun deleteAccounts()
}