package com.laiyuanwen.android.baby.love

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.databinding.ListItemLoveBinding

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class LovesAdapter(
        private val fragment: Fragment,
        private val callback: (Love) -> Unit
) : ListAdapter<Love, LovesAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val love = getItem(position)

        holder.binding.loveContent.text = love.content
        holder.binding.username.text = love.user.name

        Glide.with(fragment)
                .load(love.user.avatar)
                .into(holder.binding.avatar)

        holder.binding.root.setOnClickListener {
            callback(love)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLoveBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ListItemLoveBinding) : RecyclerView.ViewHolder(binding.root)

    class TaskDiffCallback : DiffUtil.ItemCallback<Love>() {

        override fun areContentsTheSame(oldItem: Love, newItem: Love): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Love, newItem: Love): Boolean {
            return oldItem == newItem
        }

    }
}