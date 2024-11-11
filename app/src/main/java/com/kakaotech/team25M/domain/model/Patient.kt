package com.kakaotech.team25M.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Patient (
    val patientId : Long = 0,
    val patientName: String = "",
    val patientPhone: String = "",
    val patientGender: Gender = Gender.MALE,
    val patientRelation: String = "",
    val patientBirth: String = "",
    val nokPhone: String = ""
): Parcelable
