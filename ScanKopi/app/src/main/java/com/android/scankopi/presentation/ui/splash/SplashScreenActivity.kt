package com.android.scankopi.presentation.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.android.scankopi.databinding.ActivitySplashScreenBinding
import com.android.scankopi.presentation.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private var _binding: ActivitySplashScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val splashIcon = binding.ivSplash.drawable as? AnimatedVectorDrawable

        splashIcon?.start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            }, 1500
        )
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}