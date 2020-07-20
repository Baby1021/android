package com.laiyuanwen.android.glide.loader

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import java.io.InputStream

/**
 * Created by laiyuanwen on 2020/7/19.
 */
class OSSModelLoaderFactory : ModelLoaderFactory<OSSImageData, InputStream> {
    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<OSSImageData, InputStream> {
        return OSSModelLoader()
    }

    override fun teardown() {
    }

}