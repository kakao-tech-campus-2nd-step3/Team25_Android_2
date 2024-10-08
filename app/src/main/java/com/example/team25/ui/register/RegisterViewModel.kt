package com.example.team25.ui.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.team25.data.network.dto.ManagerRegisterDto
import com.example.team25.domain.usecase.RegisterManagerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerManagerUseCase: RegisterManagerUseCase
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage

    private val _gender = MutableStateFlow("남성")
    val gender: StateFlow<String> = _gender

    private val _career = MutableStateFlow("")
    val career: StateFlow<String> = _career

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _certificateImage = MutableStateFlow("")
    val certificateImage: StateFlow<String> = _certificateImage

    private val _registerState = MutableStateFlow<Result<String>?>(null)
    val registerState: StateFlow<Result<String>?> = _registerState

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateProfileImage(newImage: String) {
        _profileImage.value = newImage
    }

    fun updateGender(newGender: String) {
        _gender.value = newGender
    }

    fun updateCareer(newCareer: String) {
        _career.value = newCareer
    }

    fun updateComment(newComment: String) {
        _comment.value = newComment
    }

    fun updateCertificateImage(newImage: String) {
        _certificateImage.value = newImage
    }

    fun registerManager(token: String) {
        val managerRegisterDto = ManagerRegisterDto(
            name = _name.value,
            profileImage = _profileImage.value,
            gender = _gender.value,
            career = _career.value,
            comment = _comment.value,
            certificateImage = _certificateImage.value
        )

        viewModelScope.launch {
            val result = registerManagerUseCase(token, managerRegisterDto)
            _registerState.value = result
        }
    }

    fun logManagerInfo() {
        val managerRegisterDto = ManagerRegisterDto(
            name = _name.value,
            profileImage = _profileImage.value,
            gender = _gender.value,
            career = _career.value,
            comment = _comment.value,
            certificateImage = _certificateImage.value
        )

        // 로그 출력
        Log.d("RegisterViewModel", "Name: ${_name.value}")
        Log.d("RegisterViewModel", "Profile Image: ${_profileImage.value}")
        Log.d("RegisterViewModel", "Gender: ${_gender.value}")
        Log.d("RegisterViewModel", "Career: ${_career.value}")
        Log.d("RegisterViewModel", "Comment: ${_comment.value}")
        Log.d("RegisterViewModel", "Certificate Image: ${_certificateImage.value}")
    }
}
