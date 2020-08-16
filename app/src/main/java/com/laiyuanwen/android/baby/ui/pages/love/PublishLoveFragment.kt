package com.laiyuanwen.android.baby.ui.pages.love

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.laiyuanwen.android.baby.Common.RequestCode.SELECT_IMAGE
import com.laiyuanwen.android.baby.api.RetrofitService
import com.laiyuanwen.android.baby.databinding.FragmentPublishLoveBinding
import com.laiyuanwen.android.baby.databinding.ListItemLoveImageBinding
import com.laiyuanwen.android.baby.extension.md5
import com.laiyuanwen.android.baby.platform.oss.BabyOSSClient
import com.laiyuanwen.android.baby.util.getUserId
import com.laiyuanwen.android.baby.util.toImageSelect
import com.laiyuanwen.android.baby.util.toast
import kotlinx.android.synthetic.main.fragment_publish_love.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File

/**
 * Created by laiyuanwen on 2020/7/25.
 */
class PublishLoveFragment : Fragment() {

    data class ImageAndPath(val bitmap: Bitmap, val path: String)

    private lateinit var binding: FragmentPublishLoveBinding
    private val images = arrayListOf<ImageAndPath>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPublishLoveBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAddImage.setOnClickListener {
            toImageSelect(this)
        }
        binding.publish.setOnClickListener {

            toast(context, images.map { imageAndPath -> imageAndPath.path }.toString())

            if (images.isEmpty()) {
                return@setOnClickListener
            }

            // 上传图片
            GlobalScope.launch {
                val url = BabyOSSClient.updateImage(images[0].path)

                val json = JsonObject()

                json.addProperty("content", et_content.text.toString())
                json.addProperty("userId", getUserId())

                val array = JsonArray()
                array.add(url)
                json.add("images", array)

                val body = RetrofitService.getBabyApi().publishLove(json).await()

                Log.d("laiyuanwen_debug", body.data.toString())
            }
        }
    }

    private fun updateImageLayout() {
        val layout = binding.layoutImages
        layout.removeAllViews()
        images.forEachIndexed { index, bean ->

            val imageView = ListItemLoveImageBinding.inflate(
                    LayoutInflater.from(binding.root.context), layout, true)

            Glide.with(this)
                    .load(bean.bitmap)
                    .into(imageView.loveImage)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == SELECT_IMAGE) {
            val uri = data.data
            val contentResolver = activity?.contentResolver

            val column = arrayOf<String>(MediaStore.Images.Media.DATA);
            val cursor = contentResolver?.query(data.data!!, column, null, null, null)
            cursor!!.moveToFirst()
            val columnIndex = cursor!!.getColumnIndex(column.get(0))

            val picturePath = cursor.getString(columnIndex)

            Log.d("PickPicture", picturePath);
            cursor.close();

            val bitmap = BitmapFactory.decodeStream(contentResolver?.openInputStream(uri!!))
            images.add(ImageAndPath(bitmap, picturePath))
            this.updateImageLayout()
        }
    }


}