package com.zhiwen.bilibilivideo.http

data class ApiResult<T>(
    var status: Int = 0,
    var message: String = "",
    var responseBody: T? = null
)