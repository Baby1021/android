package com.laiyuanwen.android.baby.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentUserBinding

/**
 * Created by laiyuanwen on 2018/12/31.
 */
class UserFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }
}