package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.data.network.dto.ProfileDto
import com.kakaotech.team25M.domain.repository.ManagerInformationRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val managerInformationRepository: ManagerInformationRepository
) {
    suspend operator fun invoke(): ProfileDto? {
        return try {
            val result = managerInformationRepository.getProfile()
            result.getOrNull()
        } catch (e: Exception) {
            null
        }
    }
}
