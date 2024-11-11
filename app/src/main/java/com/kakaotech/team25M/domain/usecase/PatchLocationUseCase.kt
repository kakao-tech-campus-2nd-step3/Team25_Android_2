package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.data.network.dto.PatchLocationDto
import com.kakaotech.team25M.domain.repository.ManagerInformationRepository
import javax.inject.Inject

class PatchLocationUseCase @Inject constructor(
    private val managerInformationRepository: ManagerInformationRepository
) {
    suspend operator fun invoke(patchLocationDto: PatchLocationDto): Result<String?> {
        return managerInformationRepository.patchLocation(patchLocationDto)
    }
}
