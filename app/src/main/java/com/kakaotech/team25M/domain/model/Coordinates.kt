package com.kakaotech.team25M.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinates(
    var latitude: Double = 35.78,
    var longitude: Double = 127.108621
): Parcelable
