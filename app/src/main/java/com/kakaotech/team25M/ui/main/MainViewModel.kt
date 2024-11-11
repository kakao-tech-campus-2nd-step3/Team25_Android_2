package com.kakaotech.team25M.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25M.di.IoDispatcher
import com.kakaotech.team25M.domain.usecase.GetNameUseCase
import com.kakaotech.team25M.domain.usecase.LogoutUseCase
import com.kakaotech.team25M.domain.usecase.WithdrawUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val withdrawUseCase: WithdrawUseCase,
    private val getNameUseCase: GetNameUseCase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ViewModel(){
    private val _name = MutableStateFlow<String>("")
    val name: StateFlow<String> = _name

    private val _withdrawEvent = MutableStateFlow<WithdrawStatus>(WithdrawStatus.Idle)
    val withdrawEvent: StateFlow<WithdrawStatus> = _withdrawEvent

    fun logout() {
        viewModelScope.launch(ioDispatcher) {
            logoutUseCase()
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            val withdrawResult = withdrawUseCase()
            _withdrawEvent.value = if (withdrawResult != null) {
                WithdrawStatus.Success
            } else {
                WithdrawStatus.Failure
            }
        }
    }

    fun getName() {
        viewModelScope.launch {
            val managerName = getNameUseCase()
            if (managerName != null) {
                _name.value = managerName
            }
        }
    }
}
