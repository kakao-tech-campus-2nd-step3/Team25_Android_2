package com.example.team25.domain.repository

import com.example.team25.data.network.dto.ManagerRegisterDto

interface ManagerRepository {
    suspend fun registerManager(managerRegisterDto: ManagerRegisterDto): Result<String>
}
