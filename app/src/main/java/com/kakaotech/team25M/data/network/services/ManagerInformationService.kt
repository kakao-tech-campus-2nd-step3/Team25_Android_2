package com.kakaotech.team25M.data.network.services

import com.kakaotech.team25M.data.network.dto.PatchCommentDto
import com.kakaotech.team25M.data.network.dto.PatchImageDto
import com.kakaotech.team25M.data.network.dto.PatchLocationDto
import com.kakaotech.team25M.data.network.dto.PatchTimeDto
import com.kakaotech.team25M.data.network.dto.ProfileDto
import com.kakaotech.team25M.data.network.response.PatchCommentResponse
import com.kakaotech.team25M.data.network.response.PatchImageResponse
import com.kakaotech.team25M.data.network.response.PatchLocationResponse
import com.kakaotech.team25M.data.network.response.PatchTimeResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface ManagerInformationService {
    @PATCH("/api/manager/image")
    suspend fun patchImage(
        @Body patchImageDto: PatchImageDto
    ): Response<PatchImageResponse>

    @PATCH("/api/manager/location")
    suspend fun patchLocation(
        @Body patchLocationDto: PatchLocationDto
    ): Response<PatchLocationResponse>

    @POST("/api/manager/time")
    suspend fun patchTime(
        @Body patchTimeDto: PatchTimeDto
    ): Response<PatchTimeResponse>

    @PATCH("/api/manager/comment")
    suspend fun patchComment(
        @Body patchCommentDto: PatchCommentDto
    ): Response<PatchCommentResponse>

    @GET("/api/manager/me/profile")
    suspend fun getProfile(): Response<ProfileDto?>
}
