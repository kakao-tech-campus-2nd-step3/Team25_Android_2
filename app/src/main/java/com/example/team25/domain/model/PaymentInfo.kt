package com.example.team25.domain.model

import java.util.Date

data class PaymentInfo(
    val paymentId: Long = 0,
    val reservationId: Long = 0,
    val paymentMethod: String = "",
    val paymentAmount: Long = 0,
    val paymentDate: Date = Date(),
    val cashReceipt: Boolean = false
)
