package com.android.scankopi.presentation.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.lifecycle.lifecycleScope
import com.android.scankopi.R
import com.android.scankopi.databinding.ActivityDetailBinding
import com.android.scankopi.helper.Util.getDateTime
import com.android.scankopi.helper.convertScaaScore
import com.android.scankopi.helper.convertSniScore
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DetailViewModel by viewModels()

    private var id: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.layoutToolbar.apply {
            btnBack.setOnClickListener {
                finish()
            }
            tvTitle.text = getString(R.string.title_detail)
        }

        id = intent.getIntExtra("test_id", 1)

        bindData()
    }

    private fun bindData() {
        lifecycleScope.launch {
            val data = viewModel.testResult(id)
            binding.apply {
                tvTimestamp.text = getDateTime(data.testTimeStamp)
                tvDesc.text = data.testDescription
                tvDetail.text = data.testDetail
                qualitySni.qualityScore = data.testSniScore
                qualityScaa.qualityScore = data.testScaaScore
                qualitySni.text = convertSniScore(data.testSniScore)
                qualityScaa.text = convertScaaScore(data.testScaaScore)
                Glide.with(this@DetailActivity)
                    .load(data.testImage.toUri()).into(ivTestPhoto)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        Glide.get(this).clearMemory()
        _binding = null
    }
}