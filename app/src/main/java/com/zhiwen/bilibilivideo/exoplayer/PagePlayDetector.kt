package com.zhiwen.bilibilivideo.exoplayer

import androidx.recyclerview.widget.RecyclerView

// 用于检测是否需要播放
class PagePlayDetector(listView:RecyclerView) {

    init{
        listView.addOnScrollListener(object:RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCREEN_STATE_OFF) { // 停止滚动开始播放
                    autoPlay()
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 如果有元素
            }
        })
    }

    private fun autoPlay() {
        TODO("Not yet implemented")
    }
}