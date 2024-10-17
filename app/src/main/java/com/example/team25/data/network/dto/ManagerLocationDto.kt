package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ManagerLocationRequest(
    @SerializedName("workingRegion") val workingRegion: String
)

data class ManagerLocationResponse(
    @SerializedName("data") val data: ManagerLocationResult,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean
)

data class ManagerLocationResult(
    @SerializedName("workingRegion") val workingRegion: String
)
