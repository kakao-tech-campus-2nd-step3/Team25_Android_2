package com.kakaotech.team25M.ui.status.interfaces

import com.kakaotech.team25M.domain.model.ReservationInfo

interface OnReservationApplyClickListener {
    fun onAcceptClicked(item: ReservationInfo)
    fun onRefuseClicked(item: ReservationInfo)
}
