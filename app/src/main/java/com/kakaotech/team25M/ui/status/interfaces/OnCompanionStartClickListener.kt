package com.kakaotech.team25M.ui.status.interfaces

import com.kakaotech.team25M.domain.model.AccompanyInfo

interface OnCompanionStartClickListener {
    fun onStartClicked(accompanyInfo: AccompanyInfo)
    fun onCompleteClicked(accompanyInfo: AccompanyInfo)
}
