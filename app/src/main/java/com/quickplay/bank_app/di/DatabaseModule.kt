package com.quickplay.bank_app.di

import android.app.Application
import androidx.room.Room
import com.quickplay.bank_app.data.db.AccountDao
import com.quickplay.bank_app.data.db.RBCDatabase
import com.quickplay.bank_app.data.db.TransactionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun provideRBCDatabase(app: Application): RBCDatabase {
        return Room.databaseBuilder(app, RBCDatabase::class.java, "movie_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideAccountDao(rbcDatabase: RBCDatabase): AccountDao {
        return rbcDatabase.accountDao()
    }

    @Singleton
    @Provides
    fun provideTransactionDao(rbcDatabase: RBCDatabase): TransactionDao {
        return rbcDatabase.transactionDao()
    }
}