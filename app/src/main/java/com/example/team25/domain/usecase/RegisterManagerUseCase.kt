package com.example.team25.domain.usecase

import com.example.team25.data.network.dto.ManagerRegisterDto
import com.example.team25.domain.repository.ManagerRepository
import javax.inject.Inject

class RegisterManagerUseCase @Inject constructor(
    private val managerRepository: ManagerRepository
) {
    suspend operator fun invoke(managerRegisterDto: ManagerRegisterDto): Result<String> {
        return managerRepository.registerManager(managerRegisterDto)
    }
}
