package com.android.scankopi.presentation.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.databinding.ActivityMainBinding
import com.android.scankopi.helper.gone
import com.android.scankopi.helper.visible
import com.android.scankopi.presentation.adapter.TestListAdapter
import com.android.scankopi.presentation.ui.about.AboutActivity
import com.android.scankopi.presentation.ui.detail.DetailActivity
import com.android.scankopi.presentation.ui.info.InfoActivity
import com.android.scankopi.presentation.ui.list.ListActivity
import com.android.scankopi.presentation.ui.model.AboutModelActivity
import com.android.scankopi.presentation.ui.scan.ScanActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var rvTest: RecyclerView
    private lateinit var rvAdapter: TestListAdapter

    private lateinit var results: List<TestResultEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        displayTestResults()
        setupAction()
    }

    private fun displayTestResults() {
        rvTest = binding.rvTestResult
        rvAdapter = TestListAdapter()

        lifecycleScope.launch {
            results = viewModel.testResults()
            rvAdapter.apply {
                if (results.isEmpty()) {
                    binding.tvNoHistory.visible()
                    rvTest.gone()
                }
                submitList(results)
                TransitionManager.beginDelayedTransition(binding.root, AutoTransition())
                rvTest.visible()

                setOnClickListener(object : TestListAdapter.OnClickListener{
                    override fun onClick(testId: Int) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        intent.putExtra("test_id", testId)
                        startActivity(intent)
                    }
                })
            }
        }

        rvTest.apply {
            val manager = LinearLayoutManager(this@MainActivity)
            layoutManager = manager
            adapter = rvAdapter
            addItemDecoration(
                DividerItemDecoration(
                    this@MainActivity,
                manager.orientation)
            )
        }
    }

    private fun setupAction() {
        binding.apply {
            cvHead.setOnClickListener {
                val intent = Intent(this@MainActivity, InfoActivity::class.java)
                startActivity(intent)
            }

            btnScan.setOnClickListener {
                val intent = Intent(this@MainActivity, ScanActivity::class.java)
                startActivity(intent)
            }

            btnAllList.setOnClickListener {
                val intent = Intent(this@MainActivity, ListActivity::class.java)
                startActivity(intent)
            }

            cvBody3.setOnClickListener {
                val intent = Intent(this@MainActivity, AboutModelActivity::class.java)
                startActivity(intent)
            }

            cvBody4.setOnClickListener {
                val intent = Intent(this@MainActivity, AboutActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        lifecycleScope.launch {
            results = viewModel.testResults()
            rvAdapter.apply {
                submitList(results)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}