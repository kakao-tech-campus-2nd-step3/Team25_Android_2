package com.kakaotech.team25M.domain.repository

import com.kakaotech.team25M.data.network.dto.ManagerRegisterDto

interface ManagerRepository {
    suspend fun registerManager(managerRegisterDto: ManagerRegisterDto): Result<String>

    suspend fun getName(): String?

}
