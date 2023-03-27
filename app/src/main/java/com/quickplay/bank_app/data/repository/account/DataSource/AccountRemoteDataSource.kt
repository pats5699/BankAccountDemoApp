package com.quickplay.bank_app.data.repository.account.DataSource

import com.quickplay.bank_app.data.Response
import com.quickplay.bank_app.data.api.RBCService
import com.quickplay.bank_app.data.model.Account

class AccountRemoteDataSource(private val rbcService: RBCService) {

    suspend fun getAccountsFromAPI(): Response<List<Account>> {
        return rbcService.getAccountsList()
    }
}