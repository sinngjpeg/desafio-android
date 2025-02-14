package com.picpay.desafio.android.framework.di

import android.content.Context
import androidx.room.Room
import com.picpay.desafio.android.BuildConfig
import com.picpay.desafio.android.common.AppCoroutinesDispatchers
import com.picpay.desafio.android.data.repository.UserRepositoryImpl
import com.picpay.desafio.android.domain.local.AppDataBase
import com.picpay.desafio.android.domain.local.UserDAO
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.framework.network.PicPayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    private const val TIMEOUT_SECONDS = 15L

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            setLevel(
                if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BODY
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            )
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun providesRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): PicPayService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
            .create(PicPayService::class.java)
    }

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDataBase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDataBase: AppDataBase): UserDAO {
        return appDataBase.userDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        service: PicPayService,
        userDAO: UserDAO,
        dispatchers: AppCoroutinesDispatchers
    ): UserRepository {
        return UserRepositoryImpl(service, userDAO, dispatchers)
    }
}
