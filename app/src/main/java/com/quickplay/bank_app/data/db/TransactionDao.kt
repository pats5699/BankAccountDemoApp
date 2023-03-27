package com.quickplay.bank_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.quickplay.bank_app.data.model.Transaction

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTransactions(transactions: List<Transaction>)

    @Query("SELECT * FROM `transaction` WHERE account_number=:accountNumber")
    fun getAllTransactions(accountNumber: String): LiveData<List<Transaction>>

    @Query("DELETE FROM `transaction` WHERE account_number=:accountNumber")
    suspend fun deleteAllTransactions(accountNumber: String)
}