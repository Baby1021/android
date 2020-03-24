package com.laiyuanwen.android.baby.ui.pages.addresssearch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.laiyuanwen.android.baby.databinding.FragmentAddressSearchBinding
import com.laiyuanwen.android.baby.repository.AddressRepository
import com.laiyuanwen.android.baby.util.location.LocationManager
import kotlinx.android.synthetic.main.fragment_address_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

/**
 * 地址选择页面
 */
class AddressSelectFragment : Fragment() {

    lateinit var binding: FragmentAddressSearchBinding
    lateinit var addressSearchListAdapter: AddressSearchListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentAddressSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager;
        addressSearchListAdapter = AddressSearchListAdapter()
        binding.rvLocationList.adapter = addressSearchListAdapter

        LocationManager.getCacheLocation()?.let {
            input.setText(it.aoiName)
            search(it.aoiName)
        }

        input.isFocusable = true
        input.requestFocus();
        imm.showSoftInput(input, 0)
        input.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    // 关闭键盘
                    imm.hideSoftInputFromWindow(input.windowToken, 0);

                    input.clearFocus()

                    search(v.text.toString())
                    true
                }
                else -> false
            }
        }
    }

    private fun search(keywords: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = AddressRepository.getInstance().searchAddress(keywords)

                withContext(Dispatchers.Main) {
                    addressSearchListAdapter.setData(result)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
