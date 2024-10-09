package com.example.team25.data.network.services

import com.example.team25.data.network.dto.ManagerRegisterDto
import com.example.team25.data.network.response.RegisterManagerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface ManagerService {
    @POST("/api/manager")
    suspend fun registerManager(
        @Body managerRegisterDto: ManagerRegisterDto
    ): Response<RegisterManagerResponse>
}
