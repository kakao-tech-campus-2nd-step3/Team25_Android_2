package com.kakaotech.team25M.di

import com.kakaotech.team25M.data.repository.DefaultManagerInformationRepository
import com.kakaotech.team25M.data.repository.DefaultManagerRepository
import com.kakaotech.team25M.data.repository.DefaultS3Repository
import com.kakaotech.team25M.data.repository.DefaultUserRepository
import com.kakaotech.team25M.domain.repository.ManagerInformationRepository
import com.kakaotech.team25M.domain.repository.ManagerRepository
import com.kakaotech.team25M.domain.repository.S3Repository
import com.kakaotech.team25M.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewmodelModule {

    @Binds
    @ViewModelScoped
    abstract fun bindManagerRepository(defaultManagerRepository: DefaultManagerRepository): ManagerRepository

    @Binds
    @ViewModelScoped
    abstract fun bindS3Repository(defaultS3Repository: DefaultS3Repository): S3Repository

    @Binds
    @ViewModelScoped
    abstract fun bindUserRepository(defaultUserRepository: DefaultUserRepository): UserRepository

    @Binds
    @ViewModelScoped
    abstract fun bindManagerInformationRepository(defaultManagerInformationRepository: DefaultManagerInformationRepository): ManagerInformationRepository
}

