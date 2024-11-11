package com.kakaotech.team25M.data.network.services

import com.kakaotech.team25M.data.network.dto.UserStatusDto
import com.kakaotech.team25M.data.network.dto.WithdrawDto
import retrofit2.http.GET
import retrofit2.Response
import retrofit2.http.DELETE

interface UserService {
    @GET("/api/users/me/status")
    suspend fun getUserStatus(): Response<UserStatusDto>

    @DELETE("/api/users/withdraw")
    suspend fun withdraw(): Response<WithdrawDto>
}

