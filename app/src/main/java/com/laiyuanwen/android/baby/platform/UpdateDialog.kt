package com.laiyuanwen.android.baby.platform

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.View
import com.laiyuanwen.android.baby.R
import kotlinx.android.synthetic.main.dialog_update.*

/**
 * Created by laiyuanwen on 2019-02-08.
 */
class UpdateDialog(
        context: Context,
        private val isForce: Boolean = true,
        var versionName: String,
        var releaseNote: String
) : Dialog(context, R.style.DialogTranslucentTheme) {

    private var onCancelListener: () -> Unit? = {}
    private var onDownloadListener: () -> Unit? = {}

    init {
        setCancelable(!isForce)
        setCanceledOnTouchOutside(!isForce)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_update)

        initView()
        setSize()
    }

    fun setOnCancelListener(listener: () -> Unit): UpdateDialog {
        onCancelListener = listener
        return this
    }

    fun setOnDownloadListener(listener: () -> Unit): UpdateDialog {
        onDownloadListener = listener
        return this
    }

    private fun initView() {
        title.text = "新版本来啦"
        version.text = "最新版本:$versionName"
        release_note.text = releaseNote

        if (isForce) {
            cancel_btn.visibility = View.GONE
            divider.visibility = View.GONE
        }

        cancel_btn.setOnClickListener {
            onCancelListener()
        }

        update_btn.setOnClickListener {
            onDownloadListener()
        }

        container_layout.setOnClickListener {
            if (!isForce) {
                dismiss()
            }
        }
    }

    override fun show() {
        if (!isShowing) {
            super.show()
        }
    }

    override fun dismiss() {
        if (isShowing) {
            super.dismiss()
        }
    }

    private fun setSize() {
        val lp = window!!.attributes
        val windowManager = window.windowManager
        val display: Display = windowManager?.defaultDisplay!!

        val point = Point()
        display.getSize(point)

        lp.width = (point.x * 0.75).toInt()
        lp.height = (point.y * 0.6).toInt()

        window!!.attributes = lp
    }
}