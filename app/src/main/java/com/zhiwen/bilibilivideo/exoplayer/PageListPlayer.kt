package com.zhiwen.bilibilivideo.exoplayer

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerView

class PageListPlayer: IListPlayer {
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var exoPlayerView: StyledPlayerView
    private lateinit var exoControllerView: StyledPlayerControlView

    init {
        exoPlayer = ExoPlayer.Builder().build()
    }

    override val attachedView: WrapperPlayerView?
        get() = TODO("Not yet implemented")
    override val isPlaying: Boolean
        get() = TODO("Not yet implemented")

    override fun inActive() {
        TODO("Not yet implemented")
    }

    override fun onActive() {
        TODO("Not yet implemented")
    }

    override fun togglePlay(attachView: WrapperPlayerView, videoUrl: String) {
        TODO("Not yet implemented")
    }

    override fun stop(release: Boolean) {
        TODO("Not yet implemented")
    }
}