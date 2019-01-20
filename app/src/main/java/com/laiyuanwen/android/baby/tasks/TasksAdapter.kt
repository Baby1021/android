package com.laiyuanwen.android.baby.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.databinding.ItemTaskBinding
import com.laiyuanwen.android.baby.bean.Task

/**
 * Created by laiyuanwen on 2019/1/1.
 */
class TasksAdapter(
        private val fragment: Fragment,
        private val callback: (Task) -> Unit
) : ListAdapter<Task, TasksAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: TasksAdapter.ViewHolder, position: Int) {
        val task = getItem(position)

        holder.binding.done.isChecked = task.done
        holder.binding.processorName.text = task.processor.name
        holder.binding.title.text = task.title
        holder.binding.description.text = task.description

        Glide.with(fragment)
                .load(task.processor.avatar)
                .into(holder.binding.processorAvatar)

        holder.binding.root.setOnClickListener {
            callback(task)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksAdapter.ViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return ViewHolder(binding)
    }

    class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    class TaskDiffCallback : DiffUtil.ItemCallback<Task>() {

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean {
            return oldItem == newItem
        }

    }
}