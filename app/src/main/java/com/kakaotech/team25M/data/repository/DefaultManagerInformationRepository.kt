package com.kakaotech.team25M.data.repository

import android.util.Log
import com.kakaotech.team25M.data.network.dto.PatchCommentDto
import com.kakaotech.team25M.data.network.dto.PatchImageDto
import com.kakaotech.team25M.data.network.dto.PatchLocationDto
import com.kakaotech.team25M.data.network.dto.PatchTimeDto
import com.kakaotech.team25M.data.network.dto.ProfileDto
import com.kakaotech.team25M.data.network.services.ManagerInformationService
import com.kakaotech.team25M.domain.repository.ManagerInformationRepository
import javax.inject.Inject

class DefaultManagerInformationRepository @Inject constructor(
    private val managerInformationService: ManagerInformationService
) : ManagerInformationRepository {

    override suspend fun getProfile(): Result<ProfileDto?> {
        return try {
            val response = managerInformationService.getProfile()
            Log.d("profile", response.body().toString())
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody)
                } else {
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Result.failure(Exception("Get profile failed"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun patchImage(patchImageDto: PatchImageDto): Result<String?> {
        return try {
            val response = managerInformationService.patchImage(patchImageDto)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody.message)
                } else {
                    Log.e(TAG, "Invalid response body or status is false")
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Log.e(TAG, "Image Patch failed with status code: ${response.code()}")
                Result.failure(Exception("Image Patch failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun patchLocation(patchLocationDto: PatchLocationDto): Result<String?> {
        return try {
            val response = managerInformationService.patchLocation(patchLocationDto)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody.message)
                } else {
                    Log.e(TAG, "Invalid response body or status is false")
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Log.e(TAG, "Location Patch failed with status code: ${response.code()}")
                Result.failure(Exception("Location Patch failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun patchTime(patchTimeDto: PatchTimeDto): Result<String?> {
        return try {
            val response = managerInformationService.patchTime(patchTimeDto)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody.message)
                } else {
                    Log.e(TAG, "Invalid response body or status is false")
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Log.e(TAG, "Time Patch failed with status code: ${response.code()}")
                Result.failure(Exception("Time Patch failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}", e)
            Result.failure(e)
        }
    }

    override suspend fun patchComment(patchCommentDto: PatchCommentDto): Result<String?> {
        return try {
            val response = managerInformationService.patchComment(patchCommentDto)
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null && responseBody.status == true) {
                    Result.success(responseBody.message)
                } else {
                    Log.e(TAG, "Invalid response body or status is false")
                    Result.failure(Exception("Invalid response"))
                }
            } else {
                Log.e(TAG, "Comment Patch failed with status code: ${response.code()}")
                Result.failure(Exception("Comment Patch failed"))
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception occurred: ${e.message}", e)
            Result.failure(e)
        }
    }

    companion object {
        private const val TAG = "ManagerInformationRepository"
    }

}
