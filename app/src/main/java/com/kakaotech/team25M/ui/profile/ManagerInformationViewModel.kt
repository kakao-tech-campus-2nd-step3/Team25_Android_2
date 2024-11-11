package com.kakaotech.team25M.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25M.data.network.dto.PatchCommentDto
import com.kakaotech.team25M.data.network.dto.PatchImageDto
import com.kakaotech.team25M.data.network.dto.PatchLocationDto
import com.kakaotech.team25M.data.network.dto.ProfileDto
import com.kakaotech.team25M.domain.model.Gender
import com.kakaotech.team25M.domain.model.ImageFolder
import com.kakaotech.team25M.domain.model.WorkTimeDomain
import com.kakaotech.team25M.domain.model.toDto
import com.kakaotech.team25M.domain.usecase.GetImageUriUseCase
import com.kakaotech.team25M.domain.usecase.GetProfileUseCase
import com.kakaotech.team25M.domain.usecase.PatchCommentUseCase
import com.kakaotech.team25M.domain.usecase.PatchImageUseCase
import com.kakaotech.team25M.domain.usecase.PatchLocationUseCase
import com.kakaotech.team25M.domain.usecase.PatchTimeUseCase
import com.kakaotech.team25M.domain.usecase.S3UploadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManagerInformationViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val getImageUriUseCase: GetImageUriUseCase,
    private val s3UploadUseCase: S3UploadUseCase,
    private val patchImageUseCase: PatchImageUseCase,
    private val patchLocationUseCase: PatchLocationUseCase,
    private val patchTimeUseCase: PatchTimeUseCase,
    private val patchCommentUseCase: PatchCommentUseCase
) : ViewModel() {

    companion object {
        private const val TAG = "ProfileViewModel"
    }

    enum class ProfileLoadStatus {
        LOADING,
        SUCCESS,
        FAILURE
    }

    private val defaultTime = "00:00"

    private val _profileLoadStatus = MutableStateFlow(ProfileLoadStatus.LOADING)
    val profileLoadStatus: StateFlow<ProfileLoadStatus> = _profileLoadStatus

    private val _imagePatched = MutableStateFlow(PatchStatus.DEFAULT)
    val imagePatched: StateFlow<PatchStatus> = _imagePatched

    private val _locationPatched = MutableStateFlow(PatchStatus.DEFAULT)
    val locationPatched: StateFlow<PatchStatus> = _locationPatched

    private val _timePatched = MutableStateFlow(PatchStatus.DEFAULT)
    val timePatched: StateFlow<PatchStatus> = _timePatched

    private val _commentPatched = MutableStateFlow(PatchStatus.DEFAULT)
    val commentPatched: StateFlow<PatchStatus> = _commentPatched

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _workingRegion = MutableStateFlow("")
    val workingRegion: StateFlow<String> = _workingRegion

    private val _profileImage = MutableStateFlow("")
    val profileImage: StateFlow<String> = _profileImage

    private val _newProfileImage = MutableStateFlow("")
    val newProfileImage: StateFlow<String> = _newProfileImage

    private val _newProfileImageUrl = MutableStateFlow("")
    val newProfileImageUrl: StateFlow<String> = _newProfileImageUrl

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    private val _profileImageUrl = MutableStateFlow("")
    val profileImageUrl: StateFlow<String> = _profileImageUrl

    private val _gender = MutableStateFlow(Gender.MALE)
    val gender: StateFlow<Gender> = _gender

    private val _career = MutableStateFlow("")
    val career: StateFlow<String> = _career

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _monStartTime = MutableStateFlow("00:00")
    val monStartTime: StateFlow<String> = _monStartTime

    private val _monEndTime = MutableStateFlow("00:00")
    val monEndTime: StateFlow<String> = _monEndTime

    private val _tueStartTime = MutableStateFlow("00:00")
    val tueStartTime: StateFlow<String> = _tueStartTime

    private val _tueEndTime = MutableStateFlow("00:00")
    val tueEndTime: StateFlow<String> = _tueEndTime

    private val _wedStartTime = MutableStateFlow("00:00")
    val wedStartTime: StateFlow<String> = _wedStartTime

    private val _wedEndTime = MutableStateFlow("00:00")
    val wedEndTime: StateFlow<String> = _wedEndTime

    private val _thuStartTime = MutableStateFlow("00:00")
    val thuStartTime: StateFlow<String> = _thuStartTime

    private val _thuEndTime = MutableStateFlow("00:00")
    val thuEndTime: StateFlow<String> = _thuEndTime

    private val _friStartTime = MutableStateFlow("00:00")
    val friStartTime: StateFlow<String> = _friStartTime

    private val _friEndTime = MutableStateFlow("00:00")
    val friEndTime: StateFlow<String> = _friEndTime

    private val _satStartTime = MutableStateFlow("00:00")
    val satStartTime: StateFlow<String> = _satStartTime

    private val _satEndTime = MutableStateFlow("00:00")
    val satEndTime: StateFlow<String> = _satEndTime

    private val _sunStartTime = MutableStateFlow("00:00")
    val sunStartTime: StateFlow<String> = _sunStartTime

    private val _sunEndTime = MutableStateFlow("00:00")
    val sunEndTime: StateFlow<String> = _sunEndTime

    fun getProfile() {
        viewModelScope.launch {
            resetProfileData()
            val profile: ProfileDto? = getProfileUseCase()
            if (profile == null) {
                Log.e(TAG, "Profile data is null or failed to load")
                _profileLoadStatus.value = ProfileLoadStatus.FAILURE
            } else {
                _profileLoadStatus.value = ProfileLoadStatus.SUCCESS
                _name.value = profile.data?.name ?: "Unknown"
                _profileImage.value = profile.data?.profileImage ?: ""
                if (_profileImage.value != "") {
                    getProfileImage(_profileImage.value)
                }
                _gender.value = if (profile.data?.gender == "여성") Gender.FEMALE else Gender.MALE
                _career.value = profile.data?.career ?: "경력 없음"
                _comment.value = profile.data?.comment ?: "한 마디를 등록해주세요"
                _workingRegion.value = profile.data?.workingRegion ?: "지역을 등록해주세요"

                val workingHour = profile.data?.workingHour
                if (workingHour != null) {
                    _monStartTime.value = workingHour.monStartTime ?: defaultTime
                    _monEndTime.value = workingHour.monEndTime ?: defaultTime
                    _tueStartTime.value = workingHour.tueStartTime ?: defaultTime
                    _tueEndTime.value = workingHour.tueEndTime ?: defaultTime
                    _wedStartTime.value = workingHour.wedStartTime ?: defaultTime
                    _wedEndTime.value = workingHour.wedEndTime ?: defaultTime
                    _thuStartTime.value = workingHour.thuStartTime ?: defaultTime
                    _thuEndTime.value = workingHour.thuEndTime ?: defaultTime
                    _friStartTime.value = workingHour.friStartTime ?: defaultTime
                    _friEndTime.value = workingHour.friEndTime ?: defaultTime
                    _satStartTime.value = workingHour.satStartTime ?: defaultTime
                    _satEndTime.value = workingHour.satEndTime ?: defaultTime
                    _sunStartTime.value = workingHour.sunStartTime ?: defaultTime
                    _sunEndTime.value = workingHour.sunEndTime ?: defaultTime
                }

            }
        }
    }

    private fun getProfileImage(s3url: String) {
        viewModelScope.launch {
            val downloadedImageUri = getImageUriUseCase(s3url)
            if (downloadedImageUri != null) {
                _profileImageUri.value = downloadedImageUri
            }
        }
    }

    fun getRegion(): String? {
        return if (_workingRegion.value != "" && _workingRegion.value != "지역을 등록해주세요") {
            _workingRegion.value
        } else {
            null
        }
    }

    fun getComment(): String? {
        return if (_comment.value != "" && _comment.value != "한 마디를 등록해주세요") {
            _comment.value
        } else {
            null
        }
    }


    fun getTime(): WorkTimeDomain {
        return WorkTimeDomain(
            monStartTime = _monStartTime.value,
            monEndTime = _monEndTime.value,
            tueStartTime = _tueStartTime.value,
            tueEndTime = _tueEndTime.value,
            wedStartTime = _wedStartTime.value,
            wedEndTime = _wedEndTime.value,
            thuStartTime = _thuStartTime.value,
            thuEndTime = _thuEndTime.value,
            friStartTime = _friStartTime.value,
            friEndTime = _friEndTime.value,
            satStartTime = _satStartTime.value,
            satEndTime = _satEndTime.value,
            sunStartTime = _sunStartTime.value,
            sunEndTime = _sunEndTime.value
        )
    }

    private fun resetProfileData() {
        _name.value = "Unknown"
        _workingRegion.value = "지역을 등록해주세요"
        _career.value = "No career info"
        _comment.value = "한 마디를 등록해주세요"
        _profileImage.value = ""
        _gender.value = Gender.MALE

        _monStartTime.value = defaultTime
        _monEndTime.value = defaultTime
        _tueStartTime.value = defaultTime
        _tueEndTime.value = defaultTime
        _wedStartTime.value = defaultTime
        _wedEndTime.value = defaultTime
        _thuStartTime.value = defaultTime
        _thuEndTime.value = defaultTime
        _friStartTime.value = defaultTime
        _friEndTime.value = defaultTime
        _satStartTime.value = defaultTime
        _satEndTime.value = defaultTime
        _sunStartTime.value = defaultTime
        _sunEndTime.value = defaultTime
    }

    fun uploadImage() {
        viewModelScope.launch {
            try {
                _newProfileImageUrl.value =
                    s3UploadUseCase(_name.value, Uri.parse(_newProfileImage.value), ImageFolder.PROFILE.path)

                patchImage()
            } catch (e: Exception) {
                Log.e(TAG, "s3 upload error")
                _imagePatched.value = PatchStatus.FAILURE
            }
        }
    }

    private fun patchImage() {
        val patchImageDto = PatchImageDto(
            newProfileImage = _newProfileImageUrl.value
        )

        viewModelScope.launch {
            val result = patchImageUseCase(patchImageDto)
            _imagePatched.value = if (result.isSuccess) {
                PatchStatus.SUCCESS
            } else {
                PatchStatus.FAILURE
            }
        }
    }

    fun patchLocation(sido: String, sigungu: String) {
        val patchLocationDto = PatchLocationDto(
            newWorkingRegion = "$sido $sigungu"
        )

        viewModelScope.launch {
            val result = patchLocationUseCase(patchLocationDto)
            _locationPatched.value = if (result.isSuccess) {
                PatchStatus.SUCCESS
            } else {
                PatchStatus.FAILURE
            }
        }
    }

    fun patchComment(comment: String) {
        val patchCommentDto = PatchCommentDto(
            newComment = comment
        )

        viewModelScope.launch {
            val result = patchCommentUseCase(patchCommentDto)
            _commentPatched.value = if (result.isSuccess) {
                PatchStatus.SUCCESS
            } else {
                PatchStatus.FAILURE
            }
        }
    }

    fun patchTime(workTime: WorkTimeDomain) {
        val patchTimeDto = workTime.toDto()

        viewModelScope.launch {
            val result = patchTimeUseCase(patchTimeDto)
            _timePatched.value = if (result.isSuccess) {
                PatchStatus.SUCCESS
            } else {
                PatchStatus.FAILURE
            }
        }
    }

    fun updateProfileImage(newImage: String) {
        _newProfileImage.value = newImage
    }

    fun isNewProfileImageEmpty(): Boolean {
        return _newProfileImage.value.isEmpty()
    }

    fun updateImagePatchStatus(status: PatchStatus) {
        _imagePatched.value = status
    }

    fun updateLocationPatchStatus(status: PatchStatus) {
        _locationPatched.value = status
    }

    fun updateTimePatchStatus(status: PatchStatus) {
        _timePatched.value = status
    }

    fun updateCommentPatchStatus(status: PatchStatus) {
        _commentPatched.value = status
    }

}
