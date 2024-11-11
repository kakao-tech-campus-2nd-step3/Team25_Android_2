package com.kakaotech.team25M.ui.status

import androidx.lifecycle.ViewModel
import com.kakaotech.team25M.domain.model.PaymentInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDateTime

class PaymentHistoryViewModel : ViewModel() {
    private var _paymentInfo = MutableStateFlow<PaymentInfo>(PaymentInfo())
    val paymentInfo: StateFlow<PaymentInfo> get() = _paymentInfo

    private var _companionHistory = MutableStateFlow<List<String>>(emptyList())
    val companionHistory: StateFlow<List<String>> get() = _companionHistory

    init {
        updatePaymentInfo(
            PaymentInfo(
                paymentId = 100, reservationId = 9600, paymentMethod = "토스", paymentAmount = 50000,
                paymentDate = LocalDateTime.now(), cashReceipt = true
            )
        )
        updateCompanionHistory(
            listOf("24.08.26 21:00 방문 픽업", "24.08.26 22:00 귀가")
        )
    }

    fun updatePaymentInfo(paymentInfo: PaymentInfo) {
        _paymentInfo.value = paymentInfo
    }

    fun updateCompanionHistory(histories: List<String>) {
        _companionHistory.value = histories
    }
}
