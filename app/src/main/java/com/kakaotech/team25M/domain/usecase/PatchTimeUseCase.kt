package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.data.network.dto.PatchTimeDto
import com.kakaotech.team25M.domain.repository.ManagerInformationRepository
import javax.inject.Inject

class PatchTimeUseCase @Inject constructor(
    private val managerInformationRepository: ManagerInformationRepository
) {
    suspend operator fun invoke(patchTimeDto: PatchTimeDto): Result<String?> {
        return managerInformationRepository.patchTime(patchTimeDto)
    }
}
