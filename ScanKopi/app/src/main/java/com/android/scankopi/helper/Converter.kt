package com.android.scankopi.helper

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import com.android.scankopi.helper.Util.getTimeStamp
import java.io.File
import java.io.FileOutputStream

fun Int.dpToPx(resources: Resources): Int {
    val density = resources.displayMetrics.density
    return (this * density).toInt()
}

fun Bitmap.bitmapToUri(context: Context): Uri {
    val imageFileDir = File(context.filesDir, "scankopi")
    imageFileDir.mkdirs()

    val file = File(imageFileDir, "${getTimeStamp()}.jpg")
    val fileOutputStream = FileOutputStream(file)
    this.compress(Bitmap.CompressFormat.JPEG, 70, fileOutputStream)
    fileOutputStream.flush()
    fileOutputStream.close()

    return FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
}

fun convertSniScore(score: Int): String {
    return when (score) {
        1 -> "Mutu 1"
        2 -> "Mutu 2"
        3 -> "Mutu 3"
        4 -> "Mutu 4a"
        5 -> "Mutu 4b"
        6 -> "Mutu 5"
        else -> "Mutu 6"
    }
}

fun convertScaaScore(score: Int): String {
    return when (score) {
        1 -> "Premium Grade"
        2 -> "Specialty Grade"
        else -> "No Grade"
    }
}