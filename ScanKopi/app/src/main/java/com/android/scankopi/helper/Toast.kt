package com.android.scankopi.helper

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

class CustomToast(private val context: Context) {
    private var toast: Toast? = null
    private var isShowing = false
    private val handler = Handler(Looper.getMainLooper())

    fun showToast(message: String) {
        if (!isShowing) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            toast?.show()

            isShowing = true

            handler.postDelayed({
                isShowing = false
            }, 2000)
        }
    }
}