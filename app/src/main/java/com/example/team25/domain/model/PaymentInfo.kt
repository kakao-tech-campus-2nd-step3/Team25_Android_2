package com.example.team25.domain.model

import java.time.LocalDateTime

data class PaymentInfo(
    val paymentId: Long = 0,
    val reservationId: Long = 0,
    val paymentMethod: String = "",
    val paymentAmount: Long = 0,
    val paymentDate: LocalDateTime = LocalDateTime.now(),
    val cashReceipt: Boolean = false
)
