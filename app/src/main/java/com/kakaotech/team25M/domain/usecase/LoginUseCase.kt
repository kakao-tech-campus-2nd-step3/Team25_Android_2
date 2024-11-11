package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.data.network.dto.TokenDto
import com.kakaotech.team25M.domain.repository.LoginRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(oauthAccessToken: String): TokenDto? {
        return loginRepository.login(oauthAccessToken)
    }
}
