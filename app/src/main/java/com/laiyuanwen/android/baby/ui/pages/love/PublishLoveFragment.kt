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
import com.laiyuanwen.android.baby.Common.RequestCode.SELECT_IMAGE
import com.laiyuanwen.android.baby.databinding.FragmentPublishLoveBinding
import com.laiyuanwen.android.baby.databinding.ListItemLoveImageBinding
import com.laiyuanwen.android.baby.extension.md5
import com.laiyuanwen.android.baby.util.getUserId
import com.laiyuanwen.android.baby.util.toImageSelect
import com.laiyuanwen.android.baby.util.toast
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
                updateImage(images[0].path)
            }
        }
    }

    suspend fun updateImage(path: String) {

        // 用户名 + 文件名 + 当前时间
        val key = "${getUserId()}_${path.substring(path.lastIndexOf('/') + 1)}_${System.currentTimeMillis()}"

//        val file = File(path)
//        println(file.())

//        val put: PutObjectRequest? = PutObjectRequest("image-baby", key, path)
//        try {
//            val putResult: PutObjectResult = BabyOSSClient.oss.putObject(put)
//            Log.d("PutObject", "UploadSuccess")
//            Log.d("ETag", putResult.getETag())
//            Log.d("RequestId", putResult.getRequestId())
//        } catch (e: ClientException) {
//            // 本地异常，如网络异常等。
//            e.printStackTrace()
//        } catch (e: ServiceException) {
//            // 服务异常。
//            Log.e("RequestId", e.getRequestId())
//            Log.e("ErrorCode", e.getErrorCode())
//            Log.e("HostId", e.getHostId())
//            Log.e("RawMessage", e.getRawMessage())
//        }
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

            Log.d("laiyuanwen_debug","file md5 : ${File(picturePath).md5()}")

        }
    }



}