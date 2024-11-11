package com.kakaotech.team25M.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.kakaotech.team25M.TokensProto.Tokens
import com.kakaotech.team25M.data.network.dto.AccountLoginDto
import com.kakaotech.team25M.data.network.dto.TokenDto
import com.kakaotech.team25M.data.network.services.SignIn
import com.kakaotech.team25M.di.TokenDataStore
import com.kakaotech.team25M.domain.repository.LoginRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class DefaultLoginRepository @Inject constructor(
    private val signIn: SignIn,
    @TokenDataStore private val tokenDataStore: DataStore<Tokens>
) : LoginRepository {
    override suspend fun login(oauthAccessToken: String): TokenDto? {
        val accountLoginDto = mapToAccountLoginDto(oauthAccessToken)

        val response = signIn.getSignIn(accountLoginDto)
        return if (response.isSuccessful) {
            response.body()?.let { tokenDto ->
                Log.d("testt", response.code().toString())
                Log.d("testt", response.body().toString())
                saveTokens(
                    tokenDto.data.accessToken,
                    tokenDto.data.refreshToken,
                    tokenDto.data.expiresIn,
                    tokenDto.data.refreshTokenExpiresIn
                )

                val savedTokens = tokenDataStore.data.first() // 확인용
                Log.d("testt", "AccessToken from DataStore: ${savedTokens.accessToken}")
                Log.d("testt", "RefreshToken from DataStore: ${savedTokens.refreshToken}")
                tokenDto
            }
        } else {
            null
        }
    }

    private fun mapToAccountLoginDto(oauthAccessToken: String): AccountLoginDto {
        return AccountLoginDto(oauthAccessToken = oauthAccessToken)
    }

    override suspend fun saveTokens(
        accessToken: String,
        refreshToken: String,
        accessTokenExpiresIn: Long,
        refreshTokenExpiresIn: Long
    ) {
        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .setAccessToken(accessToken)
                .setRefreshToken(refreshToken)
                .setAccessTokenExpiresIn(accessTokenExpiresIn)
                .setRefreshTokenExpiresIn(refreshTokenExpiresIn)
                .build()
        }

        val savedTokens = tokenDataStore.data.first()
        Log.d("testt", "AccessToken from DataStore: ${savedTokens.accessToken}")
        Log.d("testt", "RefreshToken from DataStore: ${savedTokens.refreshToken}")

    }

    override suspend fun getSavedTokens(): Tokens? {
        return tokenDataStore.data.firstOrNull()
    }

    override suspend fun logout() {
        tokenDataStore.updateData { currentTokens ->
            currentTokens.toBuilder()
                .clearAccessToken()
                .clearRefreshToken()
                .clearAccessTokenExpiresIn()
                .clearRefreshTokenExpiresIn()
                .build()
        }
    }
}
