package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

data class RefreshTokenDto(
    @SerializedName("refreshToken") val refreshToken: String,
)
