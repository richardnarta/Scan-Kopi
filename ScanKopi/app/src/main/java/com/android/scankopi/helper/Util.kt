package com.android.scankopi.helper

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import com.android.scankopi.domain.model.BoundingBox
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object Util {
    fun getTimeStamp(): String {
        val timeStampFormat = "yyyy-MM-dd-HH-mm-ss-SSS"
        return SimpleDateFormat(timeStampFormat, Locale.getDefault()).format(Date())
    }

    fun getDateTime(date: String): String {
        val year = date.slice(0..3)
        val month = date.slice(5..6)
        val day = date.slice(8..9)
        val hour = date.slice(11..12)
        val min = date.slice(14..15)

        return "$day-$month-$year | $hour:$min"
    }

    fun drawBoundingBox(bitmap: Bitmap,
                        boundingBox: List<BoundingBox>): Bitmap {
        val mutableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        val canvas = Canvas(mutableBitmap)

        val paint = Paint().apply {
            color = Color.RED
            style = Paint.Style.STROKE
            this.strokeWidth = 3F
        }

        val rectPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL
        }

        val textPaint = Paint().apply {
            color = Color.BLACK
            textSize = 36F
            strokeWidth = 1F
            isAntiAlias = true
        }

        val textClassPaint = Paint().apply {
            color = Color.BLUE
            textSize = 36F
            strokeWidth = 1F
            isAntiAlias = true
        }

        boundingBox.forEach { box ->
            canvas.drawRect(box.rectangle, paint)

            val smallRect = Rect(
                box.rectangle.left,
                box.rectangle.top - 50,
                box.rectangle.left + 120,
                box.rectangle.top
            )

            canvas.drawRect(smallRect, rectPaint)

            canvas.drawText(box.score, smallRect.left + 5f, smallRect.bottom - 5f, textPaint)

            canvas.drawText(box.className, box.rectangle.left + 5f, box.rectangle.bottom + 41f, textClassPaint)
        }
        return mutableBitmap
    }
}