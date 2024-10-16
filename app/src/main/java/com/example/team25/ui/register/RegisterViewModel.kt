package com.example.team25.ui.register

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.AmazonS3Client
import com.example.team25.data.network.dto.ManagerRegisterDto
import com.example.team25.domain.model.Gender
import com.example.team25.domain.model.ImageFolder
import com.example.team25.domain.model.toKorean
import com.example.team25.domain.usecase.RegisterManagerUseCase
import com.example.team25.domain.usecase.S3UploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerManagerUseCase: RegisterManagerUseCase,
    private val s3UploadUseCase: S3UploadUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "RegisterViewModel"
    }

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage

    private val _profileImageUrl = MutableStateFlow("")
    val profileImageUrl: StateFlow<String> = _profileImageUrl

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender: StateFlow<Gender> = _gender

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

    val manChecked: StateFlow<Boolean> =
        gender.map { it == Gender.MALE }.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val womanChecked: StateFlow<Boolean> =
        gender.map { it == Gender.FEMALE }.stateIn(viewModelScope, SharingStarted.Lazily, false)

    fun updateName(newName: String) {
        _name.value = newName
    }

    fun updateProfileImage(newImage: String) {
        _profileImage.value = newImage
    }

    fun updateGender(newGender: Gender) {
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

    private fun registerManager() {
        val managerRegisterDto = ManagerRegisterDto(
            name = _name.value,
            profileImage = _profileImageUrl.value,
            gender = _gender.value.toKorean(),
            career = _career.value,
            comment = _comment.value,
            certificateImage = _certificateImageUrl.value
        )

        viewModelScope.launch {
            val result = registerManagerUseCase(managerRegisterDto)
            _registerState.value = result

            _isRegistered.value = result.isSuccess
            Log.d(TAG, _isRegistered.value.toString())
        }
    }

    fun uploadImage() {
        viewModelScope.launch {
            try {
                _profileImageUrl.value =
                    s3UploadUseCase(_name.value, Uri.parse(_profileImage.value), ImageFolder.PROFILE.path)
                _certificateImageUrl.value =
                    s3UploadUseCase(_name.value, Uri.parse(_certificateImage.value), ImageFolder.CERTIFICATE.path)
                registerManager()
            } catch (e: Exception) {
                Log.e(TAG, "s3 upload error")
            }
        }
    }

    fun isProfileImageEmpty(): Boolean {
        return _profileImage.value.isEmpty()
    }

    fun isCertificateImageEmpty(): Boolean {
        return _certificateImage.value.isEmpty()
    }

    fun logManagerInfo() {
        Log.d(TAG, "Name: ${_name.value}")
        Log.d(TAG, "Profile Image: ${_profileImage.value}")
        Log.d(TAG, "Gender: ${_gender.value}")
        Log.d(TAG, "Career: ${_career.value}")
        Log.d(TAG, "Comment: ${_comment.value}")
        Log.d(TAG, "Certificate Image: ${_certificateImage.value}")
        Log.d(TAG, "Profile Image Url: ${_profileImageUrl.value}")
        Log.d(TAG, "Certificate Image Url: ${_certificateImageUrl.value}")
    }
}
