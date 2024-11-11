package com.kakaotech.team25M.ui.status

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakaotech.team25M.domain.model.AccompanyInfo
import com.kakaotech.team25M.domain.model.Gender
import com.kakaotech.team25M.domain.model.Patient
import com.kakaotech.team25M.domain.model.ReservationInfo
import com.kakaotech.team25M.ui.status.utils.LocationStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ReservationStatusViewModel @Inject constructor(private val manager: LocationStateManager) :
    ViewModel() {

    val isServiceRunning =
        manager.runningServiceFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val runningAccompanyId =
        manager.accompanyIdFlow.stateIn(viewModelScope, SharingStarted.Eagerly, null)

    private var _reservationStatus = MutableStateFlow<List<AccompanyInfo>>(emptyList())
    val reservationStatus: StateFlow<List<AccompanyInfo>> get() = _reservationStatus

    private var _reservationApply = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val reservationApply: StateFlow<List<ReservationInfo>> get() = _reservationApply

    private var _companionHistory = MutableStateFlow<List<ReservationInfo>>(emptyList())
    val companionHistory: StateFlow<List<ReservationInfo>> get() = _companionHistory

    /**
     * 뷰모델 테스트 데이터 입니다
     */
    init {
        val mockAccompany = listOf(
            AccompanyInfo(
                isRunningService = false,
                accompanyId = 2700,
                reservationInfo = ReservationInfo(
                    departure = "부산 남구",
                    destination = "부산대학교 병원",
                    serviceDate = Date(),
                    transportation = "택시",
                    patient = Patient(
                        patientName = "이상민",
                        patientGender = Gender.MALE,
                        patientPhone = "01012345678",
                        patientBirth = "640630"
                    )
                )
            ),
            AccompanyInfo(
                isRunningService = false,
                accompanyId = 3000,
                reservationInfo = ReservationInfo(
                    departure = "부산 서면",
                    destination = "부산대학교 병원",
                    serviceDate = Date(),
                    transportation = "자차",
                    patient = Patient(
                        patientName = "김철수",
                        patientGender = Gender.FEMALE,
                        patientPhone = "01087654321",
                        patientBirth = "720630"
                    )
                )
            )
        )

        val mockReservation = listOf(
            ReservationInfo(
                departure = "부산 진구",
                destination = "부산대학교 병원",
                serviceDate = Date(),
                transportation = "택시",
                patient = Patient(
                    patientName = "박상욱",
                    patientGender = Gender.MALE,
                    patientPhone = "01012345678",
                    patientBirth = "620630"
                )
            ),
            ReservationInfo(
                departure = "부산 서구",
                destination = "부산대학교 병원",
                serviceDate = Date(),
                transportation = "자차",
                patient = Patient(
                    patientName = "육미옥",
                    patientGender = Gender.FEMALE,
                    patientPhone = "01012345678",
                    patientBirth = "620630"
                )
            )
        )

        updateReservationStatus(mockAccompany)
        updateReservationApply(mockReservation)
        updateCompanionHistory(mockReservation)
    }

    fun updateAccompanyInfo(accompany: AccompanyInfo, isServiceRunning: Boolean){
        viewModelScope.launch {
            manager.storeId(accompany.accompanyId,isServiceRunning)
        }
    }

    fun updateReservationStatus(accompanies: List<AccompanyInfo>) {
        _reservationStatus.value = accompanies
    }

    fun updateReservationStatus(accompanyId: Long?, isRunning: Boolean) {
        val updatedStatus = _reservationStatus.value.map {
            if (it.accompanyId == accompanyId) it.copy(isRunningService = isRunning) else it
        }
        _reservationStatus.value = updatedStatus
    }

    fun updateReservationApply(reservations: List<ReservationInfo>) {
        _reservationApply.value = reservations
    }

    fun updateCompanionHistory(reservations: List<ReservationInfo>) {
        _companionHistory.value = reservations
    }

    fun addReservationStatus(reservation: ReservationInfo) {
        _reservationStatus.value += AccompanyInfo(reservationInfo = reservation)
    }

    fun addCompanionHistory(reservation: ReservationInfo) {
        _companionHistory.value += reservation
    }

    fun removeReservationStatus(accompany: AccompanyInfo) {
        _reservationStatus.value -= accompany
    }

    fun removeReservationApply(reservation: ReservationInfo) {
        _reservationApply.value -= reservation
    }
}
