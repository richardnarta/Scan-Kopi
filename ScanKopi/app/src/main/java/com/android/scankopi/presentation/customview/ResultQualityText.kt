package com.android.scankopi.presentation.customview

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.android.scankopi.R

class ResultQualityText @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
): AppCompatTextView(context, attrs) {
    private var customTypeAttribute: Int = 0
    private var textBackground: GradientDrawable

    init {
        textBackground = ContextCompat.getDrawable(context, R.drawable.result_quality_bg) as GradientDrawable
        background = textBackground
        setPadding(50, 10, 50, 10)
        setTextColor(ContextCompat.getColor(context, R.color.black))

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomQualityResultText,
            0, 0).apply {
            try {
                customTypeAttribute = getInt(R.styleable.CustomQualityResultText_qualityScore, 0)
                updateBackground(customTypeAttribute)
            } finally {
                recycle()
            }
        }
    }

    var qualityScore: Int
        get() = customTypeAttribute
        set(value) {
            customTypeAttribute = value
            updateBackground(value)
        }

    private fun updateBackground(qualityScore: Int) {
        when (qualityScore) {
            1 -> textBackground.setColor(ContextCompat.getColor(context, R.color.green))
            2 -> textBackground.setColor(ContextCompat.getColor(context, R.color.spring_green))
            3 -> textBackground.setColor(ContextCompat.getColor(context, R.color.lime))
            4 -> textBackground.setColor(ContextCompat.getColor(context, R.color.chartreuse))
            5 -> textBackground.setColor(ContextCompat.getColor(context, R.color.lemon_lime))
            6 -> textBackground.setColor(ContextCompat.getColor(context, R.color.yellow))
            7 -> textBackground.setColor(ContextCompat.getColor(context, R.color.canary_yellow))
        }
    }
}