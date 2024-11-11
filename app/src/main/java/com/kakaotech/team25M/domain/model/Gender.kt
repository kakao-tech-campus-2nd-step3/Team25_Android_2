package com.kakaotech.team25M.domain.model

enum class Gender {
    MALE, FEMALE
}

fun Gender.toKorean(): String {
    return when (this) {
        Gender.MALE -> "남성"
        Gender.FEMALE -> "여성"
    }
}
