package com.kakaotech.team25M.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class AccompanyInfo (
    var isRunningService : Boolean = false,
    val accompanyId : Long = 0,
    val reservationInfo : ReservationInfo = ReservationInfo(),
    val latitude : Double = 37.402005,
    val longitude : Double = 127.108621,
    val time : Date = Date(),
    val detail: String = ""
): Parcelable
