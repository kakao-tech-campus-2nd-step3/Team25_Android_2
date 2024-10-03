package com.example.team25.ui

import android.app.Application
import com.example.team25.BuildConfig
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ManagerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKakaoMapSdk()
    }

    private fun initializeKakaoMapSdk() {
        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}
