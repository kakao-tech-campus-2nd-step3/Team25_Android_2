package com.kakaotech.team25M.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25M.TokensProto.Tokens
import com.kakaotech.team25M.data.network.dto.TokenDto
import com.kakaotech.team25M.di.IoDispatcher
import com.kakaotech.team25M.domain.model.UserStatus
import com.kakaotech.team25M.domain.usecase.GetSavedTokensUseCase
import com.kakaotech.team25M.domain.usecase.GetUserStatusUseCase
import com.kakaotech.team25M.domain.usecase.LoginUseCase
import com.kakaotech.team25M.domain.usecase.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val getSavedTokensUseCase: GetSavedTokensUseCase,
    private val getUserStatusUseCase: GetUserStatusUseCase,
    private val logoutUseCase: LogoutUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(oauthAccessToken: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val tokenDto: TokenDto? = loginUseCase(oauthAccessToken)
            if (tokenDto != null) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("로그인 실패")
            }
        }
    }

    fun updateErrorMessage(message: String) {
        _loginState.update { currentState ->
            if (currentState is LoginState.Error) {
                currentState.copy(message = message)
            } else {
                LoginState.Error(message)
            }
        }
    }

    suspend fun getSavedTokens(): Tokens? {
        return withContext(ioDispatcher) {
            getSavedTokensUseCase()
        }
    }

    suspend fun getUserStatus(): UserStatus? {
        return try {
            val statusString = getUserStatusUseCase()
            statusString?.let { UserStatus.valueOf(it) }
        } catch (e: Exception) {
            Log.d("testt", e.message.toString())
            null
        }
    }

    fun logout() {
        viewModelScope.launch(ioDispatcher) {
            logoutUseCase()
        }
    }
}
