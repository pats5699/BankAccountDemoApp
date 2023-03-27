package com.quickplay.bank_app.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Parcelize
@Entity(tableName = "account")
data class Account(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val balance: String,
    val name: String,
    val number: String,
    val type: AccountType
) : Parcelable


enum class AccountType {
    CHEQUING,
    CREDIT_CARD,
    LOAN,
    MORTGAGE
}
