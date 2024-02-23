package com.zhiwen.bilibilivideo.http

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class MyGsonConverterFactory: Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {


        return super.responseBodyConverter(type, annotations, retrofit)
    }

//    override fun responseBodyConverter()
}