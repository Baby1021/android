package com.laiyuanwen.android.baby.ui.pages.homepage.love

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.laiyuanwen.android.baby.Common.ActivityRequestCode.LOVE_DETAIL
import com.laiyuanwen.android.baby.Common.BundleKey.FLUTTER_LOVE_DETAIL_IS_CHANGE
import com.laiyuanwen.android.baby.R
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.base.BaseFragment
import com.laiyuanwen.android.baby.bean.Surprise
import com.laiyuanwen.android.baby.databinding.FragmentLovesBinding
import com.laiyuanwen.android.baby.inject.Injector
import com.laiyuanwen.android.baby.love.LovesAdapter
import com.laiyuanwen.android.baby.love.LovesViewModel
import com.laiyuanwen.android.baby.ui.pages.homepage.surprise.ImageSurpriseDialogFragment
import com.laiyuanwen.android.baby.util.getUserId
import com.laiyuanwen.android.baby.util.setStatusBarColor
import com.laiyuanwen.android.baby.util.toPublishLove
import kotlinx.android.synthetic.main.fragment_loves.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by laiyuanwen on 2019-01-20.
 */
class LovesFragment : BaseFragment() {

    private lateinit var viewModel: LovesViewModel
    private lateinit var binding: FragmentLovesBinding
    private lateinit var adapter: LovesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBarColor(activity!!, resources.getColor(R.color.colorPrimary))
        setHasOptionsMenu(true)
        fetchSurprise()
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLovesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProviders.of(this, Injector.provideLovesViewModelFactory(requireContext()))
                .get(LovesViewModel::class.java)

        val activity = activity as AppCompatActivity
        activity.setSupportActionBar(toolbar)

        initRecyclerView()
        initRefresh()
        subscribeUI()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_love_list, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (R.id.add == item.itemId) {
            toPublishLove(this, LOVE_DETAIL)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initRecyclerView() {
        adapter = LovesAdapter(this, { content, loveId ->
            viewModel.comment(content, loveId)
        }, { love ->
            Toast.makeText(context, "❤❤❤❤❤❤❤❤❤❤❤❤", Toast.LENGTH_SHORT).show()
        })
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = layoutManager.findLastVisibleItemPosition()

                if (position == adapter.itemCount - 1) {
                    // 触发下一页
                }
            }
        })
        binding.list.adapter = adapter
    }

    private fun initRefresh() {
        binding.refresh.setOnRefreshListener {
            binding.refresh.isRefreshing = true
            viewModel.refresh()
        }
    }

    private fun subscribeUI() {
        viewModel.loves.observe(this, Observer { love ->
            adapter.submitList(love)
            binding.refresh.isRefreshing = false
        })

        viewModel.reminds.observe(this, Observer { remind ->
            if (remind.isNullOrEmpty())
                return@Observer

            val fragment = LoveRemindsFragment(remind)

            fragment.show(childFragmentManager, "");
        })

        viewModel.commentResult.observe(this, Observer {
            if (it) {
                Snackbar.make(binding.root, "评论成功", Snackbar.LENGTH_LONG).show()
                viewModel.refresh()
            } else {
                Snackbar.make(binding.root, "评论失败", Snackbar.LENGTH_LONG).show()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            LOVE_DETAIL -> {
                loveDetailResult(data)
            }
        }
    }

    private fun loveDetailResult(data: Intent?) {
        if (data == null || data.extras == null) {
            return
        }

        val bundle = data.extras ?: return
        val isRefresh = bundle.getString(FLUTTER_LOVE_DETAIL_IS_CHANGE, "false") ?: "false"

        if (isRefresh.toBoolean()) {
            binding.refresh.isRefreshing = true
            viewModel.refresh()
        }
    }

    private fun fetchSurprise() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val result = RetrofitService.getBabyApi().getSurpriseAsync(getUserId()).await()

                if (result.data == null) {
                    return@launch
                }

                val data: Surprise = result.data

                withContext(Dispatchers.Main) {
                    showSurprise(data)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun showSurprise(surprise: Surprise) {
        ImageSurpriseDialogFragment.getInstance(surprise).show(childFragmentManager, "")
    }
}