package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.data.network.dto.ManagerRegisterDto
import com.kakaotech.team25M.domain.repository.ManagerRepository
import javax.inject.Inject

class RegisterManagerUseCase @Inject constructor(
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke(managerRegisterDto: ManagerRegisterDto): Result<String> {
        return managerRepository.registerManager(managerRegisterDto)
    }
}
