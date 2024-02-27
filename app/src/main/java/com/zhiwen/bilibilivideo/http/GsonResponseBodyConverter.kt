package com.zhiwen.bilibilivideo.http

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

internal class GsonResponseBodyConverter<T>(
    private val gson: Gson,
    private val type: Type
) : Converter<ResponseBody, ApiResult<T>> {

    override fun convert(value: ResponseBody): ApiResult<T>? {
        value.use {
            if (type !is ParameterizedType || !ApiResult::class.java.isAssignableFrom(type.rawType as Class<*>)) {
                throw java.lang.RuntimeException("The return type of the method must be ApiResult<*>")
            }
            val apiResult = ApiResult<T>()
            val response = JSONObject(it.string())
            apiResult.status = response.optInt("status")
            apiResult.message = response.optString("message")
            val data1 = response.optJSONObject("data")
            if (data1 != null) {
                val argumentType = type.actualTypeArguments[0]
                val data2 = data1.optString("data")
                apiResult.responseBody = gson.fromJson(data2,argumentType)
            }
            return apiResult
        }
    }
}