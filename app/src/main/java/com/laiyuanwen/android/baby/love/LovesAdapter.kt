package com.laiyuanwen.android.baby.love

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.databinding.ListItemLoveBinding
import com.laiyuanwen.android.baby.databinding.ListItemLoveCommentBinding
import com.laiyuanwen.android.baby.databinding.ListItemLoveImageBinding
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class LovesAdapter(
        private val fragment: Fragment,
        private val commentCallback: (String, Long) -> Unit,
        private val callback: (Love) -> Unit
) : ListAdapter<Love, LovesAdapter.ViewHolder>(TaskDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val love = getItem(position)

        holder.binding.loveContent.text = love.content
        holder.binding.username.text = love.user.name
        val format = SimpleDateFormat("MM/dd HH:mm", Locale.CHINA)
        holder.binding.createTime.text = format.format(Date(love.createTime))

        holder.binding.commentBtn.setOnClickListener {
            showCommentEditDialog(love.id)
        }

        Glide.with(fragment)
                .load(love.user.avatar)
                .into(holder.binding.avatar)

        holder.binding.root.setOnClickListener {
            callback(love)
        }

        if (!love.images.isNullOrEmpty()) {
            holder.binding.imagesLayout.removeAllViews()
            love.images.forEachIndexed { index, s ->
                val imageView = ListItemLoveImageBinding.inflate(
                        LayoutInflater.from(holder.binding.root.context), holder.binding.imagesLayout, true)

                imageView.loveImage.setOnClickListener {
                    fragment.findNavController().navigate(
                            LovesFragmentDirections.actionHomeFragmentToImageDetailFragment(love.images.toTypedArray(), index))
                }
                Glide.with(fragment)
                        .load(s)
                        .into(imageView.loveImage)
            }
        } else {
            holder.binding.imagesLayout.visibility = View.GONE
        }

        if (!love.comments.isNullOrEmpty()) {
            holder.binding.commentLayout.removeAllViews()
            love.comments.forEach { comment ->
                val commentBinding = ListItemLoveCommentBinding.inflate(LayoutInflater.from(holder.binding.root.context), holder.binding.commentLayout, true)
                commentBinding.commentContent.text = comment.content
                commentBinding.username.text = comment.user.name
            }
        } else {
            holder.binding.commentLayout.visibility = View.GONE
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

    fun showCommentEditDialog(loveId: Long) {
        val input = EditText(fragment.context)
        val builder = AlertDialog.Builder(fragment.context)

        builder.setTitle("输入评论")
                .setView(input)
                .setNegativeButton("取消") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("确定") { dialog, which ->
                    val content = input.text.toString()
                    commentCallback(content, loveId)
                }
        builder.show()
    }
}