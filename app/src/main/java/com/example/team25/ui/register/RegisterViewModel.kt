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

    private val _profileImageUrl = MutableStateFlow("")
    val profileImageUrl: StateFlow<String> = _profileImageUrl

    private val _gender = MutableStateFlow("남성")
    val gender: StateFlow<String> = _gender

    private val _career = MutableStateFlow("")
    val career: StateFlow<String> = _career

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _certificateImage = MutableStateFlow("")
    val certificateImage: StateFlow<String> = _certificateImage

    private val _certificateImageUrl = MutableStateFlow("")
    val certificateImageUrl: StateFlow<String> = _certificateImageUrl

    private val _isRegistered = MutableStateFlow(false)
    val isRegistered: StateFlow<Boolean> = _isRegistered

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

    fun updateUploadedImageUrl(folderPath: String, url: String) {
        when (folderPath) {
            "images/profile" -> _profileImage.value = url
            "images/certificate" -> _certificateImage.value = url
        }
    }

    fun registerManager() {
        val managerRegisterDto = ManagerRegisterDto(
            name = _name.value,
            profileImage = _profileImageUrl.value,
            gender = _gender.value,
            career = _career.value,
            comment = _comment.value,
            certificateImage = _certificateImageUrl.value
        )

        viewModelScope.launch {
            val result = registerManagerUseCase(managerRegisterDto)
            _registerState.value = result

            _isRegistered.value = result.isSuccess
            Log.d("RegisterViewModel", _isRegistered.value.toString())
        }
    }

    fun logManagerInfo() {
        Log.d("RegisterViewModel", "Name: ${_name.value}")
        Log.d("RegisterViewModel", "Profile Image: ${_profileImage.value}")
        Log.d("RegisterViewModel", "Gender: ${_gender.value}")
        Log.d("RegisterViewModel", "Career: ${_career.value}")
        Log.d("RegisterViewModel", "Comment: ${_comment.value}")
        Log.d("RegisterViewModel", "Certificate Image: ${_certificateImage.value}")
        Log.d("RegisterViewModel", "Profile Image Url: ${_profileImageUrl.value}")
        Log.d("RegisterViewModel", "Certificate Image Url: ${_certificateImageUrl.value}")

    }
}
