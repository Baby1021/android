package com.laiyuanwen.android.baby.surprise

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseDialogFragment
import kotlinx.android.synthetic.main.fragment_surprise_text.*

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class TextSurpriseDialogFragment : BaseDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_surprise_text, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(this)
                .load(R.drawable.icon)
                .into(content_image)
    }
}