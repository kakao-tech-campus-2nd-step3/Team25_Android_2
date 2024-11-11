package com.kakaotech.team25M.ui.companion

import androidx.lifecycle.ViewModel
import com.kakaotech.team25M.domain.model.AccompanyInfo
import com.kakaotech.team25M.domain.model.Coordinates
import com.kakaotech.team25M.domain.model.Gender
import com.kakaotech.team25M.domain.model.Patient
import com.kakaotech.team25M.domain.model.ReservationInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LiveCompanionViewModel : ViewModel() {
    private val _accompanyInfo = MutableStateFlow<AccompanyInfo>(AccompanyInfo())
    val accompanyInfo: StateFlow<AccompanyInfo> = _accompanyInfo

    private val _coordinates = MutableStateFlow<Coordinates>(Coordinates())
    val coordinates: StateFlow<Coordinates> = _coordinates

    private val _statusMessages = MutableStateFlow<List<String>>(emptyList())
    val statusMessages: StateFlow<List<String>> = _statusMessages

    /**
     * 뷰모델 테스트 데이터 입니다
     */
    init {
        updateAccompanyInfo(
            AccompanyInfo(
                isRunningService = false,
                accompanyId = 7000,
                ReservationInfo(
                    departure = "부산 남구", destination = "부산대학교 병원", patient = Patient(
                        patientId = 7,
                        patientName = "문경자",
                        patientGender = Gender.FEMALE,
                        patientBirth = "640630",
                        patientPhone = "01012345678"
                    )
                ),
                detail = "추가 요청 사항 없습니다.",
                latitude = 35.78,
                longitude = 127.108621
            )
        )
    }

    fun updateAccompanyInfo(accompanyInfo: AccompanyInfo) {
        _accompanyInfo.value = accompanyInfo
    }

    fun updateCoordinates(latitude: Double, longitude: Double) {
        _coordinates.value.latitude = latitude
        _coordinates.value.longitude = longitude
    }

    fun updateStatusMessages(messages: List<String>) {
        _statusMessages.value = messages
    }

    fun addStatusMessage(message: String) {
        _statusMessages.value += message
    }
}
