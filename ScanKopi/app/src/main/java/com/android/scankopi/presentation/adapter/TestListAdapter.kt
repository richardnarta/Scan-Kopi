package com.android.scankopi.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.scankopi.R
import com.android.scankopi.data.src.local.entity.TestResultEntity
import com.android.scankopi.databinding.ListItemBinding
import com.android.scankopi.helper.Util.getDateTime
import com.android.scankopi.helper.convertScaaScore
import com.android.scankopi.helper.convertSniScore

class TestListAdapter: ListAdapter<TestResultEntity, TestListAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private var onClickListener: OnClickListener? = null

    inner class MyViewHolder(binding: ListItemBinding): RecyclerView.ViewHolder(binding.root) {
        val dateTime = binding.tvTimestamp
        val sni = binding.qualitySni
        val scaa = binding.qualityScaa
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        holder.apply {
            dateTime.text = itemView.resources.getString(
                R.string.date_template,
                getDateTime(item.testTimeStamp))
            sni.text = convertSniScore(item.testSniScore)
            scaa.text = convertScaaScore(item.testScaaScore)
            sni.qualityScore = item.testSniScore
            scaa.qualityScore = item.testScaaScore

            itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(item.testId)
                }
            }
        }
    }

    interface OnClickListener{
        fun onClick(testId: Int)
    }

    fun setOnClickListener(onClickListener: OnClickListener){
        this.onClickListener = onClickListener
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TestResultEntity>() {
            override fun areItemsTheSame(
                oldItem: TestResultEntity,
                newItem: TestResultEntity
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TestResultEntity,
                newItem: TestResultEntity
            ): Boolean {
                return oldItem.testId == newItem.testId
            }
        }
    }
}