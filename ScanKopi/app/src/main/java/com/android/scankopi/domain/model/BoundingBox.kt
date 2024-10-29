package com.android.scankopi.domain.model

import android.graphics.Rect

data class BoundingBox(
    val rectangle: Rect,
    val score: String
)
