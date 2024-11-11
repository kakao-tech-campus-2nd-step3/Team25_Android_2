package com.kakaotech.team25M.data.network.services

import com.kakaotech.team25M.data.network.dto.ManagerRegisterDto
import com.kakaotech.team25M.data.network.dto.NameDto
import com.kakaotech.team25M.data.network.dto.PatchImageDto
import com.kakaotech.team25M.data.network.response.PatchImageResponse
import com.kakaotech.team25M.data.network.response.RegisterManagerResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ManagerService {
    @POST("/api/manager")
    suspend fun registerManager(
        @Body managerRegisterDto: ManagerRegisterDto
    ): Response<RegisterManagerResponse>

    @GET("/api/manager/name")
    suspend fun getName(): Response<NameDto>
}
