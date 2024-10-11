package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class DayScheduleDto(
    @SerializedName("day") val day: String,
    @SerializedName("startTime") var startTime: String = "00:00",
    @SerializedName("endTime") var endTime: String = "00:00"
)
