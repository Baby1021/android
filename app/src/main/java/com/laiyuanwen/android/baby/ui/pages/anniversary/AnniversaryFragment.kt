package com.laiyuanwen.android.baby.ui.pages.anniversary

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentAnniversaryBinding
import com.laiyuanwen.android.baby.repository.AnniversaryRepository

/**
 * Created by laiyuanwen on 2019-06-06.
 */
class AnniversaryFragment : BaseFragment() {

    private lateinit var binding: FragmentAnniversaryBinding
    private lateinit var adapter: AnniversaryAdapter
    private lateinit var viewModel: AnniversaryViewModel
    private lateinit var addDialog: AnniversaryDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        addDialog = AnniversaryDialogFragment.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAnniversaryBinding.inflate(layoutInflater)
        adapter = AnniversaryAdapter(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AnniversaryViewModel(AnniversaryRepository.getInstance()) as T
            }

        }).get(AnniversaryViewModel::class.java)
        addDialog.viewModel = viewModel

        initToolbar()

        binding.recyclerView.adapter = adapter
        binding.refreshLayout.setOnRefreshListener { refresh() }

        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.showAddDialog.observe(this, Observer { show ->
            if (show) {
                addDialog.show(childFragmentManager, "")
            } else {
                addDialog.dismiss()
            }
        })

        viewModel.anniversaries.observe(this, Observer {
            adapter.submitList(it)
            binding.refreshLayout.isRefreshing = false
        })
    }

    private fun refresh() {
        viewModel.refresh()
    }

    private fun initToolbar() {
        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        binding.toolbar.setNavigationOnClickListener { activity.finish() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater?.inflate(R.menu.menu_love_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.add == item.itemId) {
            viewModel.showDialog()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}