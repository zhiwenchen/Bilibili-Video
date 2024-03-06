package com.zhiwen.bilibilivideo

import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.zhiwen.bilibilivideo.ext.invokeViewModel
import com.zhiwen.bilibilivideo.ui.AbsListFragment
import com.zhiwen.bilibilivideo.ui.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment:AbsListFragment() {

    private val viewModel by invokeViewModel<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.feed.collect{
                logd("collect feed")
                submitData(it)
            }
        }
    }
}