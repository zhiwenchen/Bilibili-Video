package com.zhiwen.bilibilivideo.ui

import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.paging.PagingDataAdapter
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.*
import com.zhiwen.bilibilivideo.exoplayer.WrapperPlayerView
import com.zhiwen.bilibilivideo.ext.load
import com.zhiwen.bilibilivideo.ext.setImageUrl
import com.zhiwen.bilibilivideo.ext.setTextVisibility
import com.zhiwen.bilibilivideo.ext.setVisibility
import com.zhiwen.bilibilivideo.model.*
import com.zhiwen.bilibilivideo.util.PixUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FeedAdapter constructor(private val lifecycleOwner: LifecycleOwner
):PagingDataAdapter<Feed,FeedAdapter.FeedViewHolder>(object: DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }
}) {

    override fun getItemViewType(position: Int): Int {
        val feedItem = getItem(position) ?: return 0
        return feedItem.itemType
    }

    //
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val feedItem = getItem(position) ?: return
        holder.bindFeedAuthor(feedItem.author)
        holder.bindFeedContent(feedItem.itemType.toString() + feedItem.feedsText)

        if (!holder.isVideo()) {
            holder.bindFeedImage(
                feedItem.width,
                feedItem.height,
                PixUtil.dp2px(300),
                feedItem.cover
            )
        } else {
            holder.bindVideoData(
                feedItem.width,
                feedItem.height,
                PixUtil.dp2px(300),
                feedItem.cover,
                feedItem.url
            )
        }

//        holder.bindFeedImage(feedItem.cover)
        holder.bindFeedComment(feedItem.topComment)
        holder.bindFeedLabel(feedItem.activityText)
        holder.bindFeedInteraction(feedItem.ugc)
    }

    //
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        // 这里没用ViewBinding
        val layoutResId = if (viewType == TYPE_IMAGE_TEXT) R.layout.layout_feed_type_text_image else R.layout.layout_feed_type_video
        return FeedViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false))
    }

    // 列表项可见时调用，此时添加视频自动播放检测器
    override fun onViewAttachedToWindow(holder: FeedViewHolder) {
        super.onViewAttachedToWindow(holder)
        //
    }

//    列表项被移除屏幕时调用，此时移除监听
    override fun onViewDetachedFromWindow(holder: FeedViewHolder) {
        super.onViewDetachedFromWindow(holder)
    }

    // 做一个缓存
    inner class FeedViewHolder(itemView: View):ViewHolder(itemView){
        private val authorBinding =
            LayoutFeedAuthorBinding.bind(itemView.findViewById(R.id.feed_author))
        private val feedTextBinding =
            LayoutFeedTextBinding.bind(itemView.findViewById(R.id.feed_text))
        private val feedImage: ImageView? = itemView.findViewById(R.id.feed_image)
        private val labelBinding =
            LayoutFeedLabelBinding.bind(itemView.findViewById(R.id.feed_label))
        private val commentBinding =
            LayoutFeedTopCommentBinding.bind(itemView.findViewById(R.id.feed_comment))
        private val interactionBinding =
            LayoutFeedInteractionBinding.bind(itemView.findViewById(R.id.feed_interaction))
        private val playerView: WrapperPlayerView? = itemView.findViewById(R.id.feed_video)

        fun bindFeedAuthor(author: Author?) {
            authorBinding.authorName.text = author?.name
            authorBinding.authorAvatar.setImageUrl(author?.avatar, isCircle = true)
        }

        fun bindFeedContent(feedsText: String?) {
            feedTextBinding.root.setTextVisibility(feedsText)
        }

        fun bindFeedImage(cover: String?) {
            feedImage?.setImageUrl(cover)
        }

        fun bindFeedImage(width: Int, height: Int, maxHeight: Int, cover: String?) {
            if (feedImage == null || TextUtils.isEmpty(cover)) {
                feedImage?.visibility = View.GONE
                return
            }
            val feedItem = getItem(layoutPosition) ?: return
            feedImage.visibility = View.VISIBLE
            feedImage.load(cover!!) {
                if (width <= 0 && height <= 0) {
                    setFeedImageSize(it.width, it.height, maxHeight)
                }
                if (feedItem.backgroundColor == 0) {
                    lifecycleOwner.lifecycle.coroutineScope.launch(Dispatchers.IO) {
                        val defaultColor = feedImage.context.getColor(R.color.color_theme_10)
                        val color = Palette.Builder(it).generate().getMutedColor(defaultColor)
                        feedItem.backgroundColor = color
                        withContext(lifecycleOwner.lifecycle.coroutineScope.coroutineContext) {
                            feedImage.background = ColorDrawable(feedItem.backgroundColor)
                        }
                    }
                } else {
                    feedImage.background = ColorDrawable(feedItem.backgroundColor)
                }
            }

            if (width > 0 && height > 0) {
                setFeedImageSize(width, height, maxHeight)
            }
        }

        fun bindVideoData(width: Int, height: Int, maxHeight: Int, cover: String?, url: String?) {
            url?.run {
                playerView?.run {
                    setVisibility(true)
                    bindData(width, height, cover, url, maxHeight)
//                    setListener(object : WrapperPlayerView.Listener {
//                        override fun onTogglePlay(attachView: WrapperPlayerView) {
//                            playDetector.togglePlay(attachView, url)
//                        }
//                    })
                }
            }
        }

        fun bindFeedLabel(activityText: String?) {
            labelBinding.root.text = activityText
        }

        fun bindFeedComment(topComment: TopComment?) {
            topComment?.run {
                commentBinding.commentAvatar.setImageUrl(author?.avatar)
                commentBinding.commentAuthor.text = author?.name
                commentBinding.commentText.text = commentText
                commentBinding.commentLikeCount.text = topComment.commentCount.toString()
                commentBinding.commentLikeStatus.setImageResource(if (topComment.hasLiked) R.drawable.icon_cell_liked else R.drawable.icon_cell_like)
//                commentBinding.commentPreview
            }
        }

        fun bindFeedInteraction(ugc: Ugc?) {
            ugc?.run {
                interactionBinding.interactionComment.text = commentCount.toString()
                interactionBinding.interactionLike.setIconResource(if (hasLiked) R.drawable.icon_cell_liked else R.drawable.icon_cell_like)
//                if ()
//                interactionBinding.interactionLike.text()
                interactionBinding.interactionLike.setIconTintResource(if (hasLiked) R.color.color_theme else R.color.color_3d3)
                interactionBinding.interactionDiss.setIconResource(if (hasdiss) R.drawable.icon_cell_dissed else R.drawable.icon_cell_diss)
                interactionBinding.interactionDiss.setIconTintResource(if (hasLiked) R.color.color_theme else R.color.color_3d3)
            }
        }

        fun isVideo(): Boolean {
            return getItem(layoutPosition)?.itemType == TYPE_VIDEO
        }

        private fun setFeedImageSize(width: Int, height: Int, maxHeight: Int) {
            val finalWidth: Int = PixUtil.getScreenWidth();
            val finalHeight: Int = if (width > height) {
                (height / (width * 1.0f / finalWidth)).toInt()
            } else {
                maxHeight
            }
            val params = feedImage!!.layoutParams as LinearLayout.LayoutParams
            params.width = finalWidth
            params.height = finalHeight
            params.gravity = Gravity.CENTER
            feedImage.scaleType = ImageView.ScaleType.FIT_CENTER
            feedImage.layoutParams = params
        }
    }

}