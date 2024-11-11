package com.kakaotech.team25M.ui.status

import androidx.lifecycle.ViewModel
import com.kakaotech.team25M.domain.model.ReservationInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReservationDetailsViewModel: ViewModel() {
    private var _reservationInfo = MutableStateFlow<ReservationInfo>(ReservationInfo())
    val reservationInfo: StateFlow<ReservationInfo> get() = _reservationInfo

    fun updateReservationInfo(reservationInfo: ReservationInfo) {
        _reservationInfo.value = reservationInfo
    }
}
