package com.laiyuanwen.android.baby.ui.pages.anniversary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.bean.Anniversary
import com.laiyuanwen.android.baby.bean.AnniversaryViewData
import com.laiyuanwen.android.baby.databinding.ItemAnniversaryBinding
import com.laiyuanwen.android.baby.databinding.ItemAnniversaryLoveBinding
import com.laiyuanwen.android.baby.util.toast

/**
 * Created by laiyuanwen on 2019-06-07.
 */

class AnniversaryAdapter(
        val fragment: Fragment
) : ListAdapter<AnniversaryViewData, AnniversaryAdapter.ViewHolder>(AnniversaryDiffCallback()) {

    class ViewHolder(val binding: ItemAnniversaryBinding) : RecyclerView.ViewHolder(binding.root)

    class AnniversaryDiffCallback : DiffUtil.ItemCallback<AnniversaryViewData>() {

        override fun areContentsTheSame(oldItem: AnniversaryViewData, newItem: AnniversaryViewData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: AnniversaryViewData, newItem: AnniversaryViewData): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAnniversaryBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val item = getItem(position)
        binding.anniversary = item
        binding.yearCount.setOnClickListener { toast(fragment.context, "已经${item.year}年了") }

        for (i in 1..item.year) {
            val itemBinding = ItemAnniversaryLoveBinding.inflate(LayoutInflater.from(fragment.context))
            itemBinding.root.alpha = 1 - (item.year - i) * 0.1.toFloat()
            itemBinding.year = i.toString()
            binding.yearCount.addView(itemBinding.root)
        }
    }

}