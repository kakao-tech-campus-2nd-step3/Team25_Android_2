package com.kakaotech.team25M.data.network.response

import com.google.gson.annotations.SerializedName

class PatchImageResponse (
    @SerializedName("data") val data: ProfileImageData?,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Boolean?
)

data class ProfileImageData(
    @SerializedName("profileImage") val profileImage: String?
)
