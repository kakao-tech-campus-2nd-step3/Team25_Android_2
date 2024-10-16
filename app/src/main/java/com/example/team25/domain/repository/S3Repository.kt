package com.example.team25.domain.repository

import android.net.Uri

interface S3Repository {
    suspend fun uploadImageToS3(name: String, imageUri: Uri, folderPath: String): String
}
