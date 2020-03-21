package com.laiyuanwen.android.baby.ui.pages.locationselect

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laiyuanwen.android.baby.bean.location.LocationSearch
import com.laiyuanwen.android.baby.databinding.ListItemLocationSelectBinding

/**
 * Created by laiyuanwen on 2020/3/22.
 */
class LocationListAdapter : RecyclerView.Adapter<LocationListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemLocationSelectBinding) : RecyclerView.ViewHolder(binding.root)

    private val data: Array<LocationSearch>

    init {
        data = arrayOf(
                LocationSearch("", "珠海大横琴高级人才公寓酒店", "", "", "彩虹路139号", "", ""),
                LocationSearch("", "唐家人才公寓", "", "", "唐淇路1083号", "", ""),
                LocationSearch("", "测试3", "", "", "地址", "", "")
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLocationSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.address.text = data[position].address
        holder.binding.name.text = data[position].name
    }
}