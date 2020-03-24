package com.laiyuanwen.android.baby.bean.response

data class HomeInfoUser(
        val name: String?,
        var avatar: String?,
        var addressName: String
)

/**
 * Created by laiyuanwen on 2020/3/24.
 */
data class HomeInfo(
        val user: HomeInfoUser,
        var lover: HomeInfoUser?
)