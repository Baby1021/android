package com.laiyuanwen.android.baby.extension

import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Created by laiyuanwen on 2020/8/16.
 */
fun File.md5(): String {
    val md = MessageDigest.getInstance("MD5")
    val fileInputStream = FileInputStream(this)
    val buffer = ByteArray(4096)

    while (true) {
        // FIXME: 2020/8/16 read方法能重复调用吗？
        // FIXME: 2020/8/16 读文件的时候问什么需要设置buffer
        val length = fileInputStream.read(buffer)
        if (length == -1) {
            break
        } else {
            md.update(buffer, 0, length)
        }
    }

    return BigInteger(1,md.digest()).toString(16)
}