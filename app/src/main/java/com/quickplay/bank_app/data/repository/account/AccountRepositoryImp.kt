package com.quickplay.bank_app.data.repository.account

import android.util.Log
import androidx.lifecycle.LiveData
import com.quickplay.bank_app.data.Resource
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.repository.account.DataSource.AccountLocalDataSource
import com.quickplay.bank_app.data.repository.account.DataSource.AccountRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AccountRepositoryImp(
    private val accountLocalDataSource: AccountLocalDataSource,
    private val accountRemoteDataSource: AccountRemoteDataSource
) : AccountRepository {

    private val TAG = "AccountRepositoryImp"
    override fun getAllAccounts(): LiveData<List<Account>> {
        return accountLocalDataSource.getAccountsFromDB()
    }

    override suspend fun fetchAccounts() = withContext(Dispatchers.IO) {
        try {
            val accountRemoteDataSource = accountRemoteDataSource.getAccountsFromAPI()
            if (accountRemoteDataSource.isSuccessful) {
                accountLocalDataSource.saveAccountsToDB(accountRemoteDataSource.body!!)
                Resource.Success
            } else {
                Resource.Error
            }
        } catch (e: Exception) {
            Log.d(TAG, "fetchAccounts: Got Error!")
            Resource.Error
        }
    }

    override suspend fun deleteAccounts() = withContext(Dispatchers.IO) {
        accountLocalDataSource.deleteAccountsFromDb()
    }

}