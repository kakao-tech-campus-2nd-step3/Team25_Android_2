package com.example.team25.data.repository

import com.example.team25.data.network.dto.ManagerCommentRequest
import com.example.team25.data.network.dto.ManagerCommentResponse
import com.example.team25.data.network.dto.ManagerLocationResponse
import com.example.team25.data.network.dto.ManagerTimeResponse
import com.example.team25.data.network.services.ManagerInformationService
import com.example.team25.dto.DaySchedule
import retrofit2.HttpException
import javax.inject.Inject

class DefaultManagerInformationRepository @Inject constructor(
    private val managerInformationService: ManagerInformationService
) {
    suspend fun changeManagerComment(managerId: String, commentRequest: ManagerCommentRequest): ManagerCommentResponse {
        val response = managerInformationService.changeComment(managerId, commentRequest)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }

    suspend fun changeManagerLocation(managerId: String, locationRequest: ManagerCommentRequest): ManagerLocationResponse {
        val response = managerInformationService.changeLocation(managerId, locationRequest)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }

    suspend fun registerManagerSchedule(managerId: String, schedule: DaySchedule): ManagerTimeResponse {
        val response = managerInformationService.registerManagerSchedule(managerId, schedule)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }

    suspend fun updateManagerSchedule(managerId: String, schedule: DaySchedule): ManagerTimeResponse {
        val response = managerInformationService.updateManagerSchedule(managerId, schedule)
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!
        } else {
            throw HttpException(response)
        }
    }
}
