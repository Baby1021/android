package com.laiyuanwen.android.baby.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentHomeBinding

/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 小宝贝首页
 */
class HomeFragment : BaseFragment() {

    lateinit var binding: FragmentHomeBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }
}