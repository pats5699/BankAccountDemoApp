package com.quickplay.bank_app.data.api

import android.util.Log
import com.quickplay.bank_app.data.Response
import com.quickplay.bank_app.data.changeAccountType
import com.quickplay.bank_app.data.changeTransactionType
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.model.Transaction
import com.rbc.rbcaccountlibrary.AccountProvider
import com.rbc.rbcaccountlibrary.Account as RBCAccount
import com.rbc.rbcaccountlibrary.Transaction as RBCTransaction

class RBCServiceImp : RBCService {

    override suspend fun getAccountsList(): Response<List<Account>> {
        val rbcAccounts: List<RBCAccount>
        try {
            rbcAccounts = AccountProvider.getAccountsList()
        } catch (e: Exception) {
            return Response.Error(e.message.toString())
        }
        return Response.Success(changeAccountType(rbcAccounts))
    }

    override suspend fun getTransactions(accountNumber: String): Response<List<Transaction>> {

        val rbcTransaction: List<RBCTransaction>
        try {
            rbcTransaction = AccountProvider.getTransactions(accountNumber)
        } catch (e: Exception) {
            return Response.Error(e.message.toString())
        }
        val transaction = changeTransactionType(rbcTransaction, false, accountNumber)
        return Response.Success(transaction)
    }

    override suspend fun getAdditionalCreditCardTransactions(accountNumber: String): Response<List<Transaction>> {
        val rbcTransaction: List<RBCTransaction>
        try {
            rbcTransaction = AccountProvider.getAdditionalCreditCardTransactions(accountNumber)
        } catch (e: Exception) {
            return Response.Error(e.message.toString())
        }
        val transaction = changeTransactionType(rbcTransaction, true, accountNumber)
        return Response.Success(transaction)
    }
}
