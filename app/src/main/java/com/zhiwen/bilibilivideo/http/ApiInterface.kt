package com.zhiwen.bilibilivideo.http

import com.zhiwen.bilibilivideo.model.Feed
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("feeds/queryHotFeedsList")
    suspend fun queryHotFeedsList(
        @Query("feedId") feedId: Int = 0,
        @Query("feedType") feedType: String = "all",
        @Query("pageCount") pageCount: Int = 10,
        @Query("userId") userId: Int = 0
    ):ApiResult<List<Feed>>

    // 如果不加suspend前缀，则需要返回Call对象
//    @GET("feeds/queryHotFeedsList")
//    fun queryHotFeedsList(
//        @Query("feedId") feedId: Int = 0,
//        @Query("feedType") feedType: String = "all",
//        @Query("pageCount") pageCount: Int = 10,
//        @Query("userId") userId: Int = 0
//    ): Call<ApiResult<List<Feed>>>
}