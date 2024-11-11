package com.kakaotech.team25M.data.network.response

import com.google.gson.annotations.SerializedName

data class PatchLocationResponse(
    @SerializedName("data") val data: WorkingRegionData?,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Boolean?
)

data class WorkingRegionData(
    @SerializedName("workingRegion") val workingRegion: String?
)
