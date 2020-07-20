package com.laiyuanwen.android.glide.loader

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.LibraryGlideModule
import java.io.InputStream

/**
 * Created by laiyuanwen on 2020/7/19.
 */

@GlideModule
class BabyGlideModule : LibraryGlideModule() {

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        registry.prepend(OSSImageData::class.java, InputStream::class.java, OSSModelLoaderFactory())
    }

}