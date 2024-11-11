package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.domain.repository.UserRepository
import javax.inject.Inject

class WithdrawUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return userRepository.withdraw()
    }
}
