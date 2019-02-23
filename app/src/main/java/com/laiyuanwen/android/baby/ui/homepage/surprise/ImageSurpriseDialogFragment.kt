package com.laiyuanwen.android.baby.ui.homepage.surprise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseDialogFragment
import com.laiyuanwen.android.baby.bean.Surprise
import kotlinx.android.synthetic.main.fragment_surprise_image.*

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class ImageSurpriseDialogFragment : BaseDialogFragment() {

    companion object {
        fun getInstance(surprise: Surprise): ImageSurpriseDialogFragment {
            val fragment = ImageSurpriseDialogFragment()
            val argument = Bundle()

            argument.putSerializable("surprise", surprise)

            fragment.arguments = argument
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_surprise_image, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(this)
                .load(R.drawable.test)
                .into(content_image)

        close.setOnClickListener {
            dismiss()
        }
    }
}