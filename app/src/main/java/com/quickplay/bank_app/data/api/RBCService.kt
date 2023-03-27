package com.quickplay.bank_app.data.api

import com.quickplay.bank_app.data.Response
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.model.Transaction

interface RBCService {
    /**
     *  Fetch data from the API and convert into [Account] data class.
     *  @return return response object, which contains List of [Account] if api call was successful else contains error message.
     */
    suspend fun getAccountsList(): Response<List<Account>>

    /**
     *  Fetch transaction from the API and convert into [Transaction] data class.
     *   @return return response object, which contains List of [Transaction] if api call was successful else contains error message.
     */
    suspend fun getTransactions(accountNumber: String): Response<List<Transaction>>

    /**
     *  Fetch additional transaction from the API and convert into [Transaction] data class.
     *  @param accountNumber for retrieving transactions associated with specific accountNumber.
     *   @return return response object, which contains List of [Transaction] if api call was successful else contains error message.
     */
    suspend fun getAdditionalCreditCardTransactions(accountNumber: String): Response<List<Transaction>>
}