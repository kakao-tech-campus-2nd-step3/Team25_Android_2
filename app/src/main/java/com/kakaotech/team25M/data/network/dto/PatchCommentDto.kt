package com.kakaotech.team25M.data.network.dto

import com.google.gson.annotations.SerializedName

data class PatchCommentDto (
    @SerializedName("comment") val newComment: String,
)
