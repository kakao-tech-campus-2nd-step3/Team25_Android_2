package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

data class PatchLocationDto (
    @SerializedName("workingRegion") val newWorkingRegion: String,
)
