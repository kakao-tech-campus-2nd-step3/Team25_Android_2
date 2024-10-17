package com.example.team25.data.network.services

import com.example.team25.data.network.dto.ManagerCommentRequest
import com.example.team25.data.network.dto.ManagerCommentResponse
import com.example.team25.data.network.dto.ManagerLocationResponse
import com.example.team25.data.network.dto.ManagerTimeResponse
import com.example.team25.dto.DaySchedule
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ManagerInformationService {

    @PATCH("/api/manager/comment/{manager_id}")
    suspend fun changeComment(
        @Path("manager_id") managerId: String,
        @Body commentRequest: ManagerCommentRequest
    ): Response<ManagerCommentResponse>

    @PATCH("/api/manager/location/{manager_id}")
    suspend fun changeLocation(
        @Path("manager_id") managerId: String,
        @Body locationRequest: ManagerCommentRequest
    ): Response<ManagerLocationResponse>

    @POST("/api/manager/time/{manager_id}")
    suspend fun registerManagerSchedule(
        @Path("manager_id") managerId: String,
        @Body schedule: DaySchedule
    ): Response<ManagerTimeResponse>

    @PUT("/api/manager/time/{manager_id}")
    suspend fun updateManagerSchedule(
        @Path("manager_id") managerId: String,
        @Body schedule: DaySchedule
    ): Response<ManagerTimeResponse>

}
