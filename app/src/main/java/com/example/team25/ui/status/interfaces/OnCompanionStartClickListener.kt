package com.example.team25.ui.status.interfaces

import com.example.team25.domain.model.AccompanyInfo

interface OnCompanionStartClickListener {
    fun onStartClicked(accompanyInfo: AccompanyInfo)
    fun onCompleteClicked(accompanyInfo: AccompanyInfo)
}
