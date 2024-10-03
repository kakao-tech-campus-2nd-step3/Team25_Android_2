package com.example.team25.ui.status.interfaces

import com.example.team25.domain.model.ReservationInfo

interface OnReservationApplyClickListener {
    fun onAcceptClicked(item: ReservationInfo)
    fun onRefuseClicked(item: ReservationInfo)
}
