package com.picpay.desafio.android.framework.di

import com.picpay.desafio.android.common.AppCoroutinesDispatchers
import com.picpay.desafio.android.common.CoroutinesDispatchers
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DispatcherModule {

    @Binds
    @Singleton
    abstract fun bindCoroutinesDispatchers(
        dispatchers: AppCoroutinesDispatchers
    ): CoroutinesDispatchers
}
