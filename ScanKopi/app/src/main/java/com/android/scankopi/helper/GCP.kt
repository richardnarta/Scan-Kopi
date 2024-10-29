package com.android.scankopi.helper

import android.content.Context
import android.net.Uri
import android.util.Log
import com.android.scankopi.BuildConfig
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.storage.BlobInfo
import com.google.cloud.storage.HttpMethod
import com.google.cloud.storage.Storage
import com.google.cloud.storage.StorageOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.concurrent.TimeUnit

object GCP {
    private suspend fun uploadImageFromUri(context: Context, uri: Uri, fileName: String): Boolean {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)

            val serviceAccount = context.assets.open("gcp_service_key.json")

            val storage: Storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .service

            val mimeType = "image/jpeg"

            withContext(Dispatchers.IO) {
                val bucket = storage.get(BuildConfig.BUCKET_NAME)
                bucket.create(fileName, inputStream, mimeType)
                inputStream?.close()
                true
            }
        } catch (e: Exception) {
            Log.e("ERROR", "uploadImageFromUri: ERROR", e)
            return false
        }
    }

    suspend fun getImageUrl(context: Context, uri: Uri): URL? {
        val fileName = "${Util.getTimeStamp()}.jpg"

        if (uploadImageFromUri(context, uri, fileName)) {
            val serviceAccount = context.assets.open("gcp_service_key.json")

            val storage: Storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build()
                .service

            val blobInfo = BlobInfo.newBuilder(
                BuildConfig.BUCKET_NAME,
                fileName).build()

            return storage.signUrl(
                blobInfo,
                5,
                TimeUnit.MINUTES,
                Storage.SignUrlOption.withV4Signature(),
                Storage.SignUrlOption.httpMethod(HttpMethod.GET)
            )
        } else {
            return null
        }
    }
}