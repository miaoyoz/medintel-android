package com.miaoyongzheng.medintel_android.network.wanapi

data class WanBaseResponse<out T>(
    val data: T,
    val errorCode: Int,
    val errorMsg: String
)