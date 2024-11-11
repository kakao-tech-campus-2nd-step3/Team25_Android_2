package com.kakaotech.team25M.domain.usecase

import android.net.Uri
import com.kakaotech.team25M.domain.repository.S3Repository
import javax.inject.Inject

class S3UploadUseCase @Inject constructor(
    private val s3Repository: S3Repository
) {
    suspend operator fun invoke(name: String, imageUri: Uri, folderPath: String): String {
        return s3Repository.uploadImageToS3(name, imageUri, folderPath)
    }
}
