package com.example.team25.ui

import android.app.Application
import com.amazonaws.mobile.client.AWSMobileClient
import com.amazonaws.mobileconnectors.s3.transferutility.TransferNetworkLossHandler
import com.example.team25.BuildConfig
import com.kakao.sdk.common.KakaoSdk
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ManagerApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKakaoMapSdk()
        initializeKakaoSdk()
        initializeTransferNetworkLossHandler()
    }

    private fun initializeTransferNetworkLossHandler() {
        TransferNetworkLossHandler.getInstance(this)
    }

    private fun initializeKakaoMapSdk() {
        KakaoMapSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }

    private fun initializeKakaoSdk() {
        KakaoSdk.init(this, BuildConfig.KAKAO_API_KEY)
    }
}
