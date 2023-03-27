package com.quickplay.bank_app.di

import com.quickplay.bank_app.data.model.Account
import com.quickplay.bank_app.ui.viewmodels.AccountDetailViewModel
import dagger.assisted.AssistedFactory

object ViewModelFactories {
    @AssistedFactory
    interface AccountDetailViewModelAssistedFactory {
        fun create(account: Account): AccountDetailViewModel
    }
}