package com.android.scankopi.helper

import android.content.Context
import com.android.scankopi.helper.Constant.CACHE_IMAGE_PREFIX
import java.io.File

object Cache {
    fun clearTempImages(context: Context) {
        val cacheDir = File(context.cacheDir, "scankopi")
        val tempFiles = cacheDir.listFiles { file ->
            file.name.startsWith(CACHE_IMAGE_PREFIX)
        }
        tempFiles?.forEach { file ->
            file.delete()
        }
    }
}