package com.laiyuanwen.android.baby.ui.pages.homepage.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentUserBinding
import com.laiyuanwen.android.baby.util.*

/**
 * Created by laiyuanwen on 2018/12/31.
 */
class UserFragment : BaseFragment() {

    lateinit var binding: FragmentUserBinding

    override fun onStart() {
        super.onStart()
        setStatusBarColor(activity!!, ContextCompat.getColor(context!!, R.color.baby_white))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)

        binding.setting.setOnClickListener {
            activity?.let { toSetting(it) }
        }
        binding.setLocationBtn.setOnClickListener {
            activity?.let {
                Navigation.findNavController(it, R.id.home_container).navigate(R.id.action_userFragment_to_addressSelectFragment)
            }
        }
        binding.logout.setOnClickListener {
            logout()
            toLogin(it.context)
        }

        return binding.root
    }
}