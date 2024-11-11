package com.kakaotech.team25M.data.repository

import android.util.Log
import com.kakaotech.team25M.data.network.dto.ManagerRegisterDto
import com.kakaotech.team25M.data.network.dto.PatchImageDto
import com.kakaotech.team25M.data.network.services.ManagerService
import com.kakaotech.team25M.domain.repository.ManagerRepository
import javax.inject.Inject

class DefaultManagerRepository @Inject constructor(
    private val managerService: ManagerService,
) : ManagerRepository {

    override suspend fun registerManager(managerRegisterDto: ManagerRegisterDto): Result<String> {
        return try {
            val response = managerService.registerManager(managerRegisterDto)
            Log.d(TAG, "Response: $response")
            if (response.isSuccessful) {
                val responseBody = response.body()
                Log.d(TAG, "Response Body: $responseBody")
                if (responseBody != null && responseBody.status) {
                    Result.success(responseBody.message)
                } else {
                    Log.e(TAG, "Invalid response body or status is false")
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Log.e(TAG, "Registration failed with status code: ${response.code()}")
                Result.failure(Exception("Registration failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun getName(): String? {
        val response = managerService.getName()
        return if (response.isSuccessful) {
            response.body()?.data?.name
        } else {
            null
        }
    }

    companion object {
        private const val TAG = "ManagerRepository"
    }

}
