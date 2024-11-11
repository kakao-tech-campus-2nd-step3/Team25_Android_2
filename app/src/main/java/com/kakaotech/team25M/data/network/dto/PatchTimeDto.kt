package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName
import com.kakaotech.team25M.domain.model.WorkTimeDomain

data class PatchTimeDto(
    @SerializedName("monStartTime") var monStartTime: String = "00:00",
    @SerializedName("monEndTime") var monEndTime: String = "00:00",
    @SerializedName("tueStartTime") var tueStartTime: String = "00:00",
    @SerializedName("tueEndTime") var tueEndTime: String = "00:00",
    @SerializedName("wedStartTime") var wedStartTime: String = "00:00",
    @SerializedName("wedEndTime") var wedEndTime: String = "00:00",
    @SerializedName("thuStartTime") var thuStartTime: String = "00:00",
    @SerializedName("thuEndTime") var thuEndTime: String = "00:00",
    @SerializedName("friStartTime") var friStartTime: String = "00:00",
    @SerializedName("friEndTime") var friEndTime: String = "00:00",
    @SerializedName("satStartTime") var satStartTime: String = "00:00",
    @SerializedName("satEndTime") var satEndTime: String = "00:00",
    @SerializedName("sunStartTime") var sunStartTime: String = "00:00",
    @SerializedName("sunEndTime") var sunEndTime: String = "00:00"
)

fun PatchTimeDto.toDomain(): WorkTimeDomain {
    return WorkTimeDomain(
        monStartTime = this.monStartTime,
        monEndTime = this.monEndTime,
        tueStartTime = this.tueStartTime,
        tueEndTime = this.tueEndTime,
        wedStartTime = this.wedStartTime,
        wedEndTime = this.wedEndTime,
        thuStartTime = this.thuStartTime,
        thuEndTime = this.thuEndTime,
        friStartTime = this.friStartTime,
        friEndTime = this.friEndTime,
        satStartTime = this.satStartTime,
        satEndTime = this.satEndTime,
        sunStartTime = this.sunStartTime,
        sunEndTime = this.sunEndTime
    )
}
