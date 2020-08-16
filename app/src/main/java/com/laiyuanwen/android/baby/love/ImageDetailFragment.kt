package com.laiyuanwen.android.baby.love

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentImageBinding
import com.laiyuanwen.android.baby.platform.oss.BabyOSSClient
import com.laiyuanwen.android.glide.loader.OSSImageData

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

        val url = args.images[args.index]
        Glide.with(context!!)
                .load(if (url.indexOf("aliyun") >= 0) OSSImageData(BabyOSSClient.oss, url) else url)
                .into(binding.imageDetail)

        binding.index.text = args.index.toString()

        return binding.root
    }
}