package com.quickplay.bank_app.di

import com.quickplay.bank_app.data.repository.account.AccountRepository
import com.quickplay.bank_app.data.repository.account.AccountRepositoryImp
import com.quickplay.bank_app.data.repository.account.DataSource.AccountLocalDataSource
import com.quickplay.bank_app.data.repository.account.DataSource.AccountRemoteDataSource
import com.quickplay.bank_app.data.repository.transaction.DataSource.TransactionLocalDataSource
import com.quickplay.bank_app.data.repository.transaction.DataSource.TransactionRemoteDataSource
import com.quickplay.bank_app.data.repository.transaction.TransactionRepository
import com.quickplay.bank_app.data.repository.transaction.TransactionRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAccountRepository(
        accountLocalDataSource: AccountLocalDataSource,
        accountRemoteDataSource: AccountRemoteDataSource
    ): AccountRepository {
        return AccountRepositoryImp(accountLocalDataSource, accountRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideTransactionRepository(
        transactionLocalDataSource: TransactionLocalDataSource,
        transactionRemoteDataSource: TransactionRemoteDataSource
    ): TransactionRepository {
        return TransactionRepositoryImp(transactionLocalDataSource, transactionRemoteDataSource)
    }
}