package com.laiyuanwen.android.baby.ui.takecase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentTakeCaseBinding

/**
 * Created by laiyuanwen on 2019-02-11.
 *
 * 关爱
 */
class TakeCaseFragment : BaseFragment() {

    lateinit var binding: FragmentTakeCaseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentTakeCaseBinding.inflate(layoutInflater)
        return binding.root
    }
}