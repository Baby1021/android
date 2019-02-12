package com.laiyuanwen.android.baby.ui.date

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentDateBinding

/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 纪念日
 */
class DateFragment : BaseFragment() {

    lateinit var binding: FragmentDateBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDateBinding.inflate(inflater)
        return  binding.root
    }
}