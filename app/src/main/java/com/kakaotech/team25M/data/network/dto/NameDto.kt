package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

class NameDto (
    @SerializedName("status") val status: Boolean?,
    @SerializedName("message") val message: String?,
    @SerializedName("data") val data: NameData?
)

data class NameData(
    @SerializedName("managerName") val name: String?,
)
