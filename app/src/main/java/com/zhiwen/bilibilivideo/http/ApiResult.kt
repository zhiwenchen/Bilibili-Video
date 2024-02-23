package com.zhiwen.bilibilivideo.http

data class ApiResult<T>(
    val status: Int,
    val message: String,
    val data: String
)