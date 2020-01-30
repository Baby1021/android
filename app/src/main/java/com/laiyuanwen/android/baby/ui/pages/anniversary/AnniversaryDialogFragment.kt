package com.laiyuanwen.android.baby.ui.pages.anniversary

import android.app.DatePickerDialog
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import com.laiyuanwen.android.baby.base.BaseDialogFragment
import com.laiyuanwen.android.baby.databinding.DialogAnniversaryAddBinding


/**
 * Created by laiyuanwen on 2019-06-08.
 */
class AnniversaryDialogFragment : BaseDialogFragment(), DatePickerDialog.OnDateSetListener {

    lateinit var viewModel: AnniversaryViewModel

    private lateinit var binding: DialogAnniversaryAddBinding
    private lateinit var datePickerDialog: DatePickerDialog

    companion object {
        fun getInstance(): AnniversaryDialogFragment {
            return AnniversaryDialogFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DialogAnniversaryAddBinding.inflate(inflater, container, false)
        datePickerDialog = createDatePickerDialog()
        binding.name = viewModel.name
        binding.year = viewModel.year
        binding.month = viewModel.month
        binding.day = viewModel.dayOfMonth
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initWindow()
    }

    private fun initWindow() {
        val lp = dialog?.window!!.attributes
        val windowManager = dialog?.window!!.windowManager
        val display: Display = windowManager?.defaultDisplay!!

        val point = Point()
        display.getSize(point)

        lp.width = (point.x * 0.75).toInt()
        lp.height = (point.y * 0.6).toInt()

        dialog?.window!!.attributes = lp
    }

    private fun createDatePickerDialog(): DatePickerDialog {
        return DatePickerDialog(context!!, this,
                viewModel.year,
                viewModel.month + 1,
                viewModel.dayOfMonth)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setOnCancelListener { viewModel.hideDialog() }

        binding.submit.setOnClickListener {
            viewModel.submit()
        }
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.updateName(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
        binding.dateLayout.setOnClickListener {
            datePickerDialog.show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        viewModel.updateDate(year, month + 1, dayOfMonth)
        binding.year = year
        binding.month = month + 1
        binding.day = dayOfMonth
    }
}