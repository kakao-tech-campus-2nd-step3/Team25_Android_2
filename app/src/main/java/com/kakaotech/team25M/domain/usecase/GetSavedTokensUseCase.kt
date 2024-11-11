package com.kakaotech.team25M.domain.usecase

import com.kakaotech.team25M.TokensProto.Tokens
import com.kakaotech.team25M.domain.repository.LoginRepository
import javax.inject.Inject

class GetSavedTokensUseCase @Inject constructor(
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(): Tokens? {
        return loginRepository.getSavedTokens()
    }
}
