package com.kakaotech.team25M.domain.model

import android.os.Parcelable
import com.kakaotech.team25M.data.network.dto.PatchTimeDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class WorkTimeDomain(
    var monStartTime: String = "00:00",
    var monEndTime: String = "00:00",
    var tueStartTime: String = "00:00",
    var tueEndTime: String = "00:00",
    var wedStartTime: String = "00:00",
    var wedEndTime: String = "00:00",
    var thuStartTime: String = "00:00",
    var thuEndTime: String = "00:00",
    var friStartTime: String = "00:00",
    var friEndTime: String = "00:00",
    var satStartTime: String = "00:00",
    var satEndTime: String = "00:00",
    var sunStartTime: String = "00:00",
    var sunEndTime: String = "00:00",
) : Parcelable

fun WorkTimeDomain.toDto(): PatchTimeDto {
    return PatchTimeDto(
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
