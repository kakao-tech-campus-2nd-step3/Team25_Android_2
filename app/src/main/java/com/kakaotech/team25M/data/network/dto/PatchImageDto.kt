package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

data class PatchImageDto (
    @SerializedName("profileImage") val newProfileImage: String,
)
