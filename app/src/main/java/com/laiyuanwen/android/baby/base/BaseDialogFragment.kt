package com.laiyuanwen.android.baby.base

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.laiyuanwen.android.baby.R

/**
 * Created by laiyuanwen on 2019-01-20.
 */
open class BaseDialogFragment : DialogFragment() {

    var onCancelListener: DialogInterface.OnCancelListener? = null
        set(value) {
            field = value
            if (this.dialog != null) {
                this.dialog.setOnCancelListener(value)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.DialogTranslucentTheme)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        this.dialog.setOnCancelListener(onCancelListener)
    }
}