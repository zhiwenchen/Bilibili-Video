package com.zhiwen.bilibilivideo.exoplayer

import android.view.LayoutInflater
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerControlView
import com.google.android.exoplayer2.ui.StyledPlayerView
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.util.AppGlobals

class PageListPlayer : IListPlayer {
    private lateinit var exoPlayer: ExoPlayer
    private lateinit var exoPlayerView: StyledPlayerView
    private lateinit var exoControllerView: StyledPlayerControlView

    init {
        // 视频播放器
        val application = AppGlobals.getApplication()
        exoPlayer = ExoPlayer.Builder(application).build()

        exoPlayerView =
            LayoutInflater.from(application)
                .inflate(R.layout.layout_exo_player_view, null) as StyledPlayerView
        exoControllerView = LayoutInflater.from(application)
            .inflate(R.layout.layout_exo_player_controller_view, null) as StyledPlayerControlView

        // 把播放器实例和playerView和controllerView相关联
        exoPlayerView.player = exoPlayer
        exoControllerView.player = exoPlayer

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