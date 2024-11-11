package com.kakaotech.team25M.data.network.response

import com.google.gson.annotations.SerializedName

class PatchTimeResponse (
    @SerializedName("data") val data: ProfileTimeData?,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Boolean?
)

data class ProfileTimeData(
    @SerializedName("monStartTime") var monStartTime: String?,
    @SerializedName("monEndTime") var monEndTime: String?,
    @SerializedName("tueStartTime") var tueStartTime: String?,
    @SerializedName("tueEndTime") var tueEndTime: String?,
    @SerializedName("wedStartTime") var wedStartTime: String?,
    @SerializedName("wedEndTime") var wedEndTime: String?,
    @SerializedName("thuStartTime") var thuStartTime: String?,
    @SerializedName("thuEndTime") var thuEndTime: String?,
    @SerializedName("friStartTime") var friStartTime: String?,
    @SerializedName("friEndTime") var friEndTime: String?,
    @SerializedName("satStartTime") var satStartTime: String?,
    @SerializedName("satEndTime") var satEndTime: String?,
    @SerializedName("sunStartTime") var sunStartTime: String?,
    @SerializedName("sunEndTime") var sunEndTime: String?
)

