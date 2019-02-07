package com.laiyuanwen.android.baby.love

import android.graphics.Point
import android.os.Bundle
import android.view.Display
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.laiyuanwen.android.baby.bean.Love
import com.laiyuanwen.android.baby.databinding.FragmentLoveRemindsBinding
import com.laiyuanwen.android.baby.databinding.ListItemLoveImageBinding
import com.laiyuanwen.android.baby.databinding.ListItemLoveRemindsBinding
import com.laiyuanwen.android.baby.repository.LoveRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


/**
 * Created by laiyuanwen on 2019-02-04.
 */
class LoveRemindsFragment(
        private val reminds: List<Love>
) : DialogFragment() {

    lateinit var binding: FragmentLoveRemindsBinding
    lateinit var repository: LoveRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = LoveRepository.getInstance()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoveRemindsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.username.text = reminds[0].user.name
        binding.indicator.text = "1 / ${reminds.size}"
        binding.viewPage.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                exposureRemind(reminds[position].id)
                binding.indicator.text = "${position + 1} / ${reminds.size}"
            }

        })
        binding.viewPage.adapter = object : PagerAdapter() {

            override fun instantiateItem(container: ViewGroup, position: Int): Any {

                val binding = ListItemLoveRemindsBinding.inflate(LayoutInflater.from(context), container, false)
                container.addView(binding.root)

                binding.loveContent.text = reminds[position].content

                if (reminds[position].images.isNullOrEmpty()) {
                    return binding.root
                }

                reminds[position].images?.forEachIndexed { index, s ->
                    val imageView = ListItemLoveImageBinding.inflate(
                            LayoutInflater.from(binding.root.context), binding.imagesLayout, true)

                    imageView.loveImage.setOnClickListener {
                        this@LoveRemindsFragment.findNavController().navigate(
                                LovesFragmentDirections.actionHomeFragmentToImageDetailFragment(reminds[0].images?.toTypedArray()!!, index))
                    }
                    Glide.with(this@LoveRemindsFragment)
                            .load(s)
                            .into(imageView.loveImage)
                }

                return binding.root
            }

            override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
                if (o is View)
                    container.removeView(o)
            }

            override fun isViewFromObject(view: View, o: Any): Boolean = view == o

            override fun getCount() = reminds.size
        }


        binding.viewPage.currentItem = 0

        exposureRemind(reminds[0].id)

        Glide.with(this)
                .load(reminds[0].user.avatar)
                .into(binding.avatar)

    }

    // todo Dialog宽高
    override fun onResume() {
        super.onResume()
        val lp = dialog.window!!.attributes
        val windowManager = activity?.windowManager
        val display: Display = windowManager?.defaultDisplay!!

        val point = Point()
        display.getSize(point)

        lp.width = (point.x * 0.8).toInt()
        lp.height = (point.y * 0.6).toInt()

        dialog.window!!.attributes = lp
    }

    fun exposureRemind(loveId: Long) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                repository.exposureRemind(loveId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


    }
}