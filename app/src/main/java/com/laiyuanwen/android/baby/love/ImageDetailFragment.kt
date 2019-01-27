package com.laiyuanwen.android.baby.love

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentImageBinding

/**
 * Created by laiyuanwen on 2019-01-27.
 */
class ImageDetailFragment : BaseFragment() {

    lateinit var args: ImageDetailFragmentArgs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            args = ImageDetailFragmentArgs.fromBundle(arguments!!)
        } else {
            findNavController().popBackStack()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding = FragmentImageBinding.inflate(inflater, container, false);

        Glide.with(context!!)
                .load(args.images[args.index])
                .into(binding.imageDetail)

        binding.index.text = args.index.toString()

        return binding.root
    }
}