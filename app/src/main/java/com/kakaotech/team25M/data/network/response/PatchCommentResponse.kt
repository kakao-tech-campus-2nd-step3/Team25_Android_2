package com.kakaotech.team25M.data.network.response

import com.google.gson.annotations.SerializedName

data class PatchCommentResponse(
    @SerializedName("data") val data: CommentData?,
    @SerializedName("message") val message: String?,
    @SerializedName("status") val status: Boolean?
)

data class CommentData(
    @SerializedName("comment") val comment: String?
)
