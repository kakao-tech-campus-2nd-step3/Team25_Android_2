package com.kakaotech.team25M.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class ReservationInfo(
    val managerId: String = "",
    val departure: String = "",
    val destination: String = "",
    val serviceDate: Date = Date(),
    val serviceType: String = "",
    val transportation: String = "",
    val price: Int = 0,
    val patient: Patient = Patient(),
    val request: String = "요청사항 없음"
) : Parcelable
