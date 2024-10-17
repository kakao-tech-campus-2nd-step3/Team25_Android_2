package com.example.team25.data.network.dto

import com.example.team25.dto.DaySchedule
import com.google.gson.annotations.SerializedName

data class ManagerTimeResponse(
    @SerializedName("data") val data: ManagerTimeResult,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean
)

data class ManagerTimeResult(
    @SerializedName("daySchedule") val daySchedule: DaySchedule
)
