package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.domain.repository.ManagerRepository
import javax.inject.Inject

class GetNameUseCase @Inject constructor(
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke(): String? {
        return managerRepository.getName()
    }
}
