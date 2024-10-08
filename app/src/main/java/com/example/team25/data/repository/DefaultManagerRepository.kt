package com.example.team25.data.repository

import com.example.team25.data.network.dto.ManagerRegisterDto
import com.example.team25.data.network.services.ManagerService
import com.example.team25.domain.repository.ManagerRepository
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerService: ManagerService
) : ManagerRepository {

    override suspend fun registerManager(token: String, managerRegisterDto: ManagerRegisterDto): Result<String> {
        return try {
            val response = managerService.registerManager("Bearer $token", managerRegisterDto)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status) {
                    Result.success(responseBody.message)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
