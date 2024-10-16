package com.example.team25.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility
import com.amazonaws.services.s3.model.ObjectMetadata
import com.example.team25.domain.repository.S3Repository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import java.io.FileOutputStream
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DefaultS3Repository @Inject constructor(
    private val transferUtility: TransferUtility,
    @ApplicationContext private val context: Context
) : S3Repository {

    override suspend fun uploadImageToS3(name: String, imageUri: Uri, folderPath: String): String {
        return suspendCancellableCoroutine { continuation ->
            try {
                val fileName = generateFileName(name)
                val s3FilePath = "$folderPath/$fileName"
                val tempFile = File.createTempFile(fileName, null)

                context.contentResolver.openInputStream(imageUri).use { input ->
                    FileOutputStream(tempFile).use { output ->
                        input?.copyTo(output)
                    }
                }

                val metadata = ObjectMetadata().apply {
                    contentType = context.contentResolver.getType(imageUri)
                }

                val observer = transferUtility.upload(
                    "manager-app-storage",
                    s3FilePath,
                    tempFile,
                    metadata
                )

                observer.setTransferListener(object : TransferListener {
                    override fun onStateChanged(id: Int, state: TransferState?) {
                        if (state == TransferState.COMPLETED) {
                            val uploadedUrl =
                                "https://manager-app-storage.s3.ap-northeast-2.amazonaws.com/$s3FilePath"
                            continuation.resume(uploadedUrl)
                        }
                    }

                    override fun onProgressChanged(id: Int, bytesCurrent: Long, bytesTotal: Long) {
                        Log.d("S3 Upload", "Upload progress: $bytesCurrent/$bytesTotal")
                    }

                    override fun onError(id: Int, ex: Exception?) {
                        Log.e("S3 Upload", "Error: ${ex?.message}")
                        continuation.resumeWithException(ex ?: Exception("Unknown error"))
                    }
                })

            } catch (e: Exception) {
                continuation.resumeWithException(e)
            }
        }
    }

    private fun generateFileName(name: String): String {
        val dateFormat: DateFormat = SimpleDateFormat("yyMMddHHmmss", Locale.getDefault())
        val currentDateTime = dateFormat.format(Date())

        return "$name$currentDateTime"
    }
}
