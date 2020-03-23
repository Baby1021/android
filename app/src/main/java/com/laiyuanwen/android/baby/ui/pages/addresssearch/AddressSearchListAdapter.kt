package com.laiyuanwen.android.baby.ui.pages.addresssearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.bean.location.AddressSearch
import com.laiyuanwen.android.baby.databinding.ListItemLocationSelectBinding
import com.laiyuanwen.android.baby.repository.AddressRepository
import com.laiyuanwen.android.baby.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_image.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by laiyuanwen on 2020/3/22.
 */
class AddressSearchListAdapter : RecyclerView.Adapter<AddressSearchListAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListItemLocationSelectBinding) : RecyclerView.ViewHolder(binding.root)

    private var data: Array<AddressSearch> = emptyArray()

    fun setData(data: Array<AddressSearch>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemLocationSelectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val location = data[position]
        holder.binding.address.text = location.address
        holder.binding.name.text = location.name
        holder.binding.root.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val result = AddressRepository.getInstance().saveAddress(location.id)

                    withContext(Dispatchers.Main) {
                        toast(holder.binding.root.context, result)
                        Navigation.findNavController(holder.binding.root).popBackStack()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}