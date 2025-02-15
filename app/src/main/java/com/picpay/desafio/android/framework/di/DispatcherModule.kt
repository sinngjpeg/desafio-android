package com.picpay.desafio.android.framework.di

import com.picpay.desafio.core.usecase.base.AppCoroutinesDispatchers
import com.picpay.desafio.core.usecase.base.CoroutinesDispatchers
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
        dispatchers: com.picpay.desafio.core.usecase.base.AppCoroutinesDispatchers
    ): com.picpay.desafio.core.usecase.base.CoroutinesDispatchers
}
