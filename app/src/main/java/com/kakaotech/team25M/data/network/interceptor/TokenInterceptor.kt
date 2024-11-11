package com.kakaotech.team25M.data.network.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import androidx.datastore.core.DataStore
import com.kakaotech.team25M.TokensProto.Tokens
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    private val dataStore: DataStore<Tokens>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            val tokens = dataStore.data.first()
            tokens.accessToken
        }

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()

        return chain.proceed(request)
    }
}
