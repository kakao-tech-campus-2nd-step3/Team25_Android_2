package com.kakaotech.team25M.domain.repository

import com.kakaotech.team25M.data.network.dto.UserStatus

interface UserRepository {
    suspend fun getUserStatus(): UserStatus?

    suspend fun withdraw(): String?
}
