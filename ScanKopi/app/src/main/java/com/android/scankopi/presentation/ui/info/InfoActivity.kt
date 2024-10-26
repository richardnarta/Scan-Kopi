package com.android.scankopi.presentation.ui.info

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.scankopi.R
import com.android.scankopi.databinding.ActivityInfoBinding

class InfoActivity : AppCompatActivity() {
    private var _binding: ActivityInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityInfoBinding.inflate(layoutInflater)

        binding.layoutToolbar.apply {
            btnBack.setOnClickListener {
                finish()
            }
            tvTitle.text = getString(R.string.title_info)
        }

        setContentView(binding.root)
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}