package com.picpay.desafio.android.com.picpay.desafio.android.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object BaseUrlTestModule {

    @Provides
    fun provideBaseUrl(): String = "http://localhost:9090/"
}