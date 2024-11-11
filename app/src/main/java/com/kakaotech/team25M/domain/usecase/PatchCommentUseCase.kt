package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.data.network.dto.PatchCommentDto
import com.kakaotech.team25M.domain.repository.ManagerInformationRepository
import javax.inject.Inject

class PatchCommentUseCase @Inject constructor(
    private val managerInformationRepository: ManagerInformationRepository
) {
    suspend operator fun invoke(patchCommentDto: PatchCommentDto): Result<String?> {
        return managerInformationRepository.patchComment(patchCommentDto)
    }
}
