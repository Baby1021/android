package com.laiyuanwen.android.baby.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.databinding.FragmentTasksBinding
import com.laiyuanwen.android.baby.inject.Injector
import com.laiyuanwen.android.baby.util.Provider

/**
 * Created by laiyuanwen on 2018/12/31.
 */
class TasksFragment : BaseFragment() {

    private lateinit var viewModel: TasksViewModel
    private lateinit var binding: FragmentTasksBinding
    private lateinit var adapter: TasksAdapter

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, Injector.provideTasksViewModelFactory(requireContext()))
                .get(TasksViewModel::class.java)

        initRecyclerView()
        initRefresh()
        subscribeUI()
    }

    private fun initRecyclerView() {
        adapter = TasksAdapter(this) { task ->
//            findNavController().navigate(TasksFragmentDirections.actionHomeFragmentToDetailActivity(Provider.getGson().toJson(task)))
        }
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findLastVisibleItemPosition()

                if (position == adapter.itemCount - 1) {
                    // 触发下一页
                    Snackbar.make(recyclerView, "已经滑到底部啦", Snackbar.LENGTH_LONG).show()
                }
            }
        })
        binding.list.adapter = adapter
    }

    private fun initRefresh() {
        binding.refresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }


    private fun subscribeUI() {
        viewModel.tasks.observe(this, Observer { task ->
            adapter.submitList(task)
            binding.refresh.isRefreshing = false
        })
    }
}