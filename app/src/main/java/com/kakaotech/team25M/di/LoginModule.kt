package com.kakaotech.team25M.di

import com.kakaotech.team25M.data.repository.DefaultLoginRepository
import com.kakaotech.team25M.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LoginModule {
    @Binds
    abstract fun bindLoginRepository(loginRepositoryImpl: DefaultLoginRepository): LoginRepository
}
