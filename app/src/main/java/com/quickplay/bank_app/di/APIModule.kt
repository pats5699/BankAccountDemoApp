package com.quickplay.bank_app.di

import com.quickplay.bank_app.data.api.RBCService
import com.quickplay.bank_app.data.api.RBCServiceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object APIModule {

    @Singleton
    @Provides
    fun provideBlogService(): RBCService {
        return RBCServiceImp()
    }
}