package com.example.team25.di

import com.example.team25.BuildConfig
import com.example.team25.data.network.services.ManagerService
import com.example.team25.data.remote.SignIn
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    @GeneralRetrofit
    fun provideRetrofit(): Retrofit {
        val url = BuildConfig.API_BASE_URL
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideSignIn(@GeneralRetrofit retrofit: Retrofit): SignIn {
        return retrofit.create(SignIn::class.java)
    }
    
    @Provides
    @Singleton
    fun provideManagerService(@GeneralRetrofit retrofit: Retrofit): ManagerService {
        return retrofit.create(ManagerService::class.java)
    }
}
