package com.quickplay.bank_app.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "transaction")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val amount: String,
    val date: Date,
    val description: String,
    @ColumnInfo(name = "additional_transaction")
    val additionalTransaction: Boolean,
    @ColumnInfo(name = "account_number")
    val accountNumber: String
)
