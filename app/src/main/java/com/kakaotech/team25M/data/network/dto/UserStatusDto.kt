package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

data class UserStatusDto(
    @SerializedName("status") val status: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("data") val data: UserStatus
)

data class UserStatus(
    @SerializedName("status") val userStatus: String
)
