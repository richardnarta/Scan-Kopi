package com.android.scankopi.presentation.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.scankopi.R
import com.android.scankopi.databinding.ActivityListBinding
import com.android.scankopi.helper.gone
import com.android.scankopi.helper.visible
import com.android.scankopi.presentation.adapter.TestListAdapter
import com.android.scankopi.presentation.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {
    private var _binding: ActivityListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListViewModel by viewModels()

    private lateinit var rvTest: RecyclerView
    private lateinit var rvAdapter: TestListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityListBinding.inflate(layoutInflater)

        binding.layoutToolbar.apply {
            btnBack.setOnClickListener {
                finish()
            }
            tvTitle.text = getString(R.string.title_history)
        }

        setContentView(binding.root)

        displayTestResults()
    }

    private fun displayTestResults() {
        rvTest = binding.rvTestResult
        rvAdapter = TestListAdapter()

        lifecycleScope.launch {
            val results = viewModel.testResults()
            rvAdapter.apply {
                if (results.isEmpty()) {
                    binding.tvNoHistory.visible()
                    rvTest.gone()
                }
                submitList(results)
                rvTest.visible()
                setOnClickListener(object : TestListAdapter.OnClickListener{
                    override fun onClick(testId: Int) {
                        val intent = Intent(this@ListActivity, DetailActivity::class.java)
                        intent.putExtra("test_id", testId)
                        startActivity(intent)
                    }
                })
            }
        }

        rvTest.apply {
            val manager = LinearLayoutManager(this@ListActivity)
            layoutManager = manager
            adapter = rvAdapter
            addItemDecoration(DividerItemDecoration(
                this@ListActivity,
                manager.orientation))
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}