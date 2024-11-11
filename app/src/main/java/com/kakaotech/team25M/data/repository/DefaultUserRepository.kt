package com.kakaotech.team25M.data.repository

import com.kakaotech.team25M.data.network.dto.UserStatus
import com.kakaotech.team25M.data.network.services.UserService
import com.kakaotech.team25M.domain.repository.UserRepository
import javax.inject.Inject

class DefaultUserRepository @Inject constructor(
    private val userService: UserService
) : UserRepository {
    override suspend fun getUserStatus(): UserStatus? {
        val response = userService.getUserStatus()
        return if (response.isSuccessful) {
            response.body()?.data
        } else {
            null
        }
    }

    override suspend fun withdraw(): String? {
        val response = userService.withdraw()
        return if (response.isSuccessful) {
            response.body()?.message
        } else {
            null
        }
    }
}
