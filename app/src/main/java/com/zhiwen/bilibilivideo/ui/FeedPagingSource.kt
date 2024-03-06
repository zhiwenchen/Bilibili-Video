package com.zhiwen.bilibilivideo.ui

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.zhiwen.bilibilivideo.http.ApiResult
import com.zhiwen.bilibilivideo.http.ApiService
import com.zhiwen.bilibilivideo.logd
import com.zhiwen.bilibilivideo.model.Feed


class FeedPagingSource:PagingSource<Long, Feed>() {

    override fun getRefreshKey(state: PagingState<Long, Feed>): Long? {
        return null
    }

    override suspend fun load(params: LoadParams<Long>): LoadResult<Long, Feed> {
        logd("params.key: $params.key")
        val result =
            kotlin.runCatching { ApiService.getService().getFeeds(feedId = params.key ?: 0L) }
        val apiResult = result.getOrDefault(ApiResult())
        if (apiResult.isSuccess() && apiResult.responseBody?.isNotEmpty() == true) {
            return LoadResult.Page(apiResult.responseBody!!,null,apiResult.responseBody!!.last().id)
        }
        return if (params.key == null) LoadResult.Page(arrayListOf(),null,0)
        else LoadResult.Error(java.lang.RuntimeException("No more data to fetch"))
    }
}