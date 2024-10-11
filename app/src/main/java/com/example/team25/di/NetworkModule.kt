package com.example.team25.di

import androidx.datastore.core.DataStore
import com.example.team25.BuildConfig
import com.example.team25.TokensProto.Tokens
import com.example.team25.data.network.interceptor.TokenInterceptor
import com.example.team25.data.network.services.ManagerService
import com.example.team25.data.remote.SignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @GeneralOkHttpClient
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .build()
    }

    @Provides
    @Singleton
    @SignInOkHttpClient
    fun provideSignInOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .build()
    }

    @Provides
    @Singleton
    @GeneralRetrofit
    fun provideRetrofit(@GeneralOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val url = BuildConfig.API_BASE_URL
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @SignInRetrofit
    fun provideSignInRetrofit(@SignInOkHttpClient okHttpClient: OkHttpClient): Retrofit {
        val url = BuildConfig.API_BASE_URL
        return Retrofit.Builder()
            .baseUrl(url)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignIn(@SignInRetrofit retrofit: Retrofit): SignIn {
        return retrofit.create(SignIn::class.java)
    }

    @Provides
    @Singleton
    fun provideManagerService(@GeneralRetrofit retrofit: Retrofit): ManagerService {
        return retrofit.create(ManagerService::class.java)
    }

    @Provides
    @Singleton
    fun provideTokenInterceptor(@TokenDataStore dataStore: DataStore<Tokens>): TokenInterceptor {
        return TokenInterceptor(dataStore)
    }
}
