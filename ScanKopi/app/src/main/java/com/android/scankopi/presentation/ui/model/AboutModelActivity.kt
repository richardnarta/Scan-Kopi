package com.android.scankopi.presentation.ui.model

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.scankopi.R
import com.android.scankopi.databinding.ActivityAboutModelBinding

class AboutModelActivity : AppCompatActivity() {
    private var _binding: ActivityAboutModelBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAboutModelBinding.inflate(layoutInflater)

        binding.layoutToolbar.apply {
            btnBack.setOnClickListener {
                finish()
            }
            tvTitle.text = getString(R.string.title_about_model)
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}