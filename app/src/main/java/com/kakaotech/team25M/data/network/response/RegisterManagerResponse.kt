package com.kakaotech.team25M.data.network.response

import com.google.gson.annotations.SerializedName

data class RegisterManagerResponse(
    @SerializedName("data") val data: Map<String, Any>,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean
)
