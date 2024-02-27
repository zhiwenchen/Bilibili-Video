package com.zhiwen.bilibilivideo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class HomeViewModel: ViewModel(){

    val feed = Pager(PagingConfig(
        pageSize = 10,
        initialLoadSize = 10,
        enablePlaceholders = false,
        prefetchDistance = 1
    ), pagingSourceFactory = { FeedPagingSource()
    }).flow.cachedIn(viewModelScope)
}