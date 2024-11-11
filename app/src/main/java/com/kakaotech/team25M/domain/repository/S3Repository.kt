package com.kakaotech.team25M.domain.repository

import android.net.Uri

interface S3Repository {
    suspend fun uploadImageToS3(name: String, imageUri: Uri, folderPath: String): String

    suspend fun downloadImageFromS3(s3Url: String): Uri?
}
