package com.android.scankopi.presentation.ui.about

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.scankopi.R
import com.android.scankopi.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private var _binding: ActivityAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutBinding.inflate(layoutInflater)

        binding.layoutToolbar.apply {
            btnBack.setOnClickListener {
                finish()
            }
            tvTitle.text = getString(R.string.about_us)
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}