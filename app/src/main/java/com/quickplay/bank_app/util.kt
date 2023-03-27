package com.quickplay.bank_app.data

import android.annotation.SuppressLint
import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import com.quickplay.bank_app.Constant
import com.quickplay.bank_app.R
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.model.AccountType
import com.quickplay.bank_app.data.model.Transaction
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import com.rbc.rbcaccountlibrary.Account as RBCAccount

/**
 * @return  date in string type yyyy-MMMM-dd. Eg: 2000-August-01
 */
@SuppressLint("SimpleDateFormat")
fun getDate(date: Date): String {
    val dateFormatter = SimpleDateFormat(Constant.DATE_FORMAT)
    return dateFormatter.format(date)
}

/**
 * @return  date in string type formate Hour:Minute AM/PM
 */
@SuppressLint("SimpleDateFormat")
fun getTime(date: Date): String {
    val dateFormat = SimpleDateFormat(Constant.TIME_FORMAT)
    return dateFormat.format(date)
}

/**
 * Error Snackbar.
 */
@SuppressLint("ResourceAsColor")
fun showErrorSnackBar(message: String, activity: Activity) {
    val snackbar = Snackbar.make(
        activity.findViewById(android.R.id.content),
        message,
        Snackbar.LENGTH_LONG
    )
    snackbar.view.setBackgroundColor(R.color.colorSnackBarError)
    snackbar.show()
}

/**
 * Convert String number in currency formate Eg: "12223" = "$12,223"
 */
fun formatNumberCurrency(number: String): String {
    val formatter = NumberFormat.getCurrencyInstance()
    return formatter.format(number.toDouble())
}

/**
 * Sealed class to store status of API calls.
 */
sealed class Resource {
    object Success : Resource()
    object Loading : Resource()
    object Error : Resource()
}

/**
 * Sealed class to store response of the API call.
 */
sealed class Response<T>(
    val body: T? = null,
    val isSuccessful: Boolean,
    val message: String? = null
) {
    class Success<T>(data: T) : Response<T>(data, true)
    class Error<T>(message: String) : Response<T>(isSuccessful = false, message = message)
}

/**
 * Change API's Account type to app's account type object.
 */
fun changeAccountType(rbcAccounts: List<RBCAccount>): List<Account> {
    val accounts = mutableListOf<Account>()
    for (rbcAccount in rbcAccounts) {
        rbcAccount.let {
            val account = Account(
                balance = it.balance,
                name = it.name,
                number = it.number,
                type = AccountType.valueOf(it.type.name)
            )
            accounts.add(account)
        }
    }
    return accounts
}
/**
 * Change API's Transaction type to app's Transaction type object.
 */
fun changeTransactionType(
    rbcTransactions: List<com.rbc.rbcaccountlibrary.Transaction>,
    isAdditionalCreditCardTransaction: Boolean,
    accountNumber: String
): List<Transaction> {
    val transactions = mutableListOf<Transaction>()
    for (rbcTransaction in rbcTransactions) {
        rbcTransaction.let {
            val account = Transaction(
                amount = it.amount,
                date = it.date.time,
                description = it.description,
                additionalTransaction = isAdditionalCreditCardTransaction,
                accountNumber = accountNumber
            )
            transactions.add(account)
        }
    }
    return transactions
}
