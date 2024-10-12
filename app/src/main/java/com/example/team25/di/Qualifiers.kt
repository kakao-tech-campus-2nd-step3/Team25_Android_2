package com.example.team25.di

import javax.inject.Qualifier


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenDataStore

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SignInOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class GeneralOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SignInRetrofit
