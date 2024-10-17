package com.example.team25.data.network.dto

import com.google.gson.annotations.SerializedName

data class ManagerCommentRequest(
    @SerializedName("comment") val comment: String
)

data class ManagerCommentResponse(
    @SerializedName("data") val data: ManagerCommentResult,
    @SerializedName("message") val message: String,
    @SerializedName("status") val status: Boolean
)

data class ManagerCommentResult(
    @SerializedName("comment") val comment: String
)
