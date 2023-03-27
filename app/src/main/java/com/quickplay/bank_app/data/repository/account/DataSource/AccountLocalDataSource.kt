package com.quickplay.bank_app.data.repository.account.DataSource

import androidx.lifecycle.LiveData
import com.quickplay.bank_app.data.db.AccountDao
import com.quickplay.bank_app.data.model.Account

class AccountLocalDataSource(private val accountDao: AccountDao) {

    fun getAccountsFromDB(): LiveData<List<Account>> {
        return accountDao.getAccounts()
    }

    suspend fun saveAccountsToDB(accounts: List<Account>) {
        accountDao.saveAccounts(accounts)
    }

    suspend fun deleteAccountsFromDb() {
        accountDao.deleteAccounts()
    }
}