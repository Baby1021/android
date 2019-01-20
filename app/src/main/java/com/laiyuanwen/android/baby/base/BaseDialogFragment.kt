package com.laiyuanwen.android.baby.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.laiyuanwen.android.baby.R

/**
 * Created by laiyuanwen on 2019-01-20.
 */
open class BaseDialogFragment : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTranslucentTheme)
    }
}