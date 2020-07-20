package com.laiyuanwen.android.glide.loader

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey
import java.io.InputStream


/**
 * Created by laiyuanwen on 2020/7/19.
 */
class OSSModelLoader : ModelLoader<OSSImageData, InputStream> {
    override fun buildLoadData(model: OSSImageData, width: Int, height: Int, options: Options): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(ObjectKey(model), OSSDataFetcher(model))
    }

    override fun handles(model: OSSImageData): Boolean =
            model.url.indexOf("oss-cn-shenzhen.aliyuncs.com") >= 0
}
