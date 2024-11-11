package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

data class ManagerRegisterDto(
    @SerializedName("name") val name: String,
    @SerializedName("profileImage") val profileImage: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("career") val career: String,
    @SerializedName("comment") val comment: String,
    @SerializedName("certificateImage") val certificateImage: String
)
