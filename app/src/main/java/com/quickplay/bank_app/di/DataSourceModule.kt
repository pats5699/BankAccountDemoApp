package com.quickplay.bank_app.di

import com.quickplay.bank_app.data.api.RBCService
import com.quickplay.bank_app.data.db.AccountDao
import com.quickplay.bank_app.data.db.TransactionDao
import com.quickplay.bank_app.data.repository.account.DataSource.AccountLocalDataSource
import com.quickplay.bank_app.data.repository.account.DataSource.AccountRemoteDataSource
import com.quickplay.bank_app.data.repository.transaction.DataSource.TransactionLocalDataSource
import com.quickplay.bank_app.data.repository.transaction.DataSource.TransactionRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideLocalAccountDataSource(accountDao: AccountDao): AccountLocalDataSource {
        return AccountLocalDataSource(accountDao)
    }

    @Singleton
    @Provides
    fun provideLocalTransactionDataSource(transactionDao: TransactionDao): TransactionLocalDataSource {
        return TransactionLocalDataSource(transactionDao)
    }

    @Singleton
    @Provides
    fun provideRemoteAccountDataSource(rbcService: RBCService): AccountRemoteDataSource {
        return AccountRemoteDataSource(rbcService)
    }

    @Singleton
    @Provides
    fun provideRemoteTransactionDataSource(rbcService: RBCService): TransactionRemoteDataSource {
        return TransactionRemoteDataSource(rbcService)
    }

}