package com.example.team25.ui.status

import androidx.lifecycle.ViewModel
import com.example.team25.domain.model.ReservationInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReservationDetailsViewModel: ViewModel() {
    private var _reservationInfo = MutableStateFlow<ReservationInfo>(ReservationInfo())
    val reservationInfo: StateFlow<ReservationInfo> get() = _reservationInfo

    fun updateReservationInfo(reservationInfo: ReservationInfo) {
        _reservationInfo.value = reservationInfo
    }
}
