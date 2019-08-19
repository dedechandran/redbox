package com.example.redboxapps.ui

import dagger.Module
import dagger.Provides

@Module
class MainModule {
    @Provides
    fun provideMainPresenter(mainPresenter: MainPresenter): MainContract.Presenter = mainPresenter
}