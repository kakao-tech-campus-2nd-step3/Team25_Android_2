package com.kakaotech.team25M.domain.repository

import com.kakaotech.team25M.data.network.dto.TokenDto
import com.kakaotech.team25M.TokensProto.Tokens

interface LoginRepository {
    suspend fun login(oauthAccessToken: String): TokenDto?

    suspend fun saveTokens(accessToken: String, refreshToken: String, accessTokenExpiresIn: Long, refreshTokenExpiresIn: Long)

    suspend fun getSavedTokens(): Tokens?

    suspend fun logout()

}
