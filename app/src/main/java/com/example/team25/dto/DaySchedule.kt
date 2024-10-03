package com.example.team25.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DaySchedule(
    val day: String,
    var start_time: String = "00:00",
    var end_time: String = "00:00"
) : Parcelable

