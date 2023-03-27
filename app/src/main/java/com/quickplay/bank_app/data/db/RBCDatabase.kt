package com.quickplay.bank_app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.data.model.Transaction

@TypeConverters(Converters::class)
@Database(
    entities = [Account::class, Transaction::class],
    version = 1,
    exportSchema = false
)
abstract class RBCDatabase : RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
}