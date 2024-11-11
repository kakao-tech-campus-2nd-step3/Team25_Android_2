package com.kakaotech.team25M.di

import androidx.datastore.core.DataStore
import com.kakaotech.team25M.BuildConfig
import com.kakaotech.team25M.TokensProto.Tokens
import com.kakaotech.team25M.data.network.authenticator.HttpAuthenticator
import com.kakaotech.team25M.data.network.interceptor.TokenInterceptor
import com.kakaotech.team25M.data.network.services.ManagerInformationService
import com.kakaotech.team25M.data.network.services.ManagerService
import com.kakaotech.team25M.data.network.services.UserService
import com.kakaotech.team25M.data.network.services.SignIn
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
    fun provideOkHttpClient(tokenInterceptor: TokenInterceptor, httpAuthenticator: HttpAuthenticator): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .authenticator(httpAuthenticator)
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
    fun provideUserService(@GeneralRetrofit retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @Singleton
    fun provideManagerInformationService(@GeneralRetrofit retrofit: Retrofit): ManagerInformationService {
        return retrofit.create(ManagerInformationService::class.java)
    }


    @Provides
    @Singleton
    fun provideTokenInterceptor(@TokenDataStore dataStore: DataStore<Tokens>): TokenInterceptor {
        return TokenInterceptor(dataStore)
    }
}
