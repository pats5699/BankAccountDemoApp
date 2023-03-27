package com.quickplay.bank_app.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.quickplay.bank_app.data.model.Account

@Dao
interface AccountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAccounts(accounts: List<Account>)

    @Query("SELECT * FROM account")
    fun getAccounts(): LiveData<List<Account>>

    @Query("DELETE FROM account")
    suspend fun deleteAccounts()

}