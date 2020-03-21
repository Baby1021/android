package com.laiyuanwen.android.baby.ui.pages.locationselect

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.laiyuanwen.android.baby.databinding.FragmentLocationSelectBinding


/**
 * 地址选择页面
 */
class LocationSelectFragment : Fragment() {

    lateinit var binding: FragmentLocationSelectBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLocationSelectBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.rvLocationList.adapter = LocationListAdapter()
    }

}
