package com.zhiwen.bilibilivideo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.*
import com.zhiwen.bilibilivideo.ext.setImageUrl
import com.zhiwen.bilibilivideo.ext.setTextVisibility
import com.zhiwen.bilibilivideo.model.*

class FeedAdapter:PagingDataAdapter<Feed,FeedAdapter.FeedViewHolder>(object: DiffUtil.ItemCallback<Feed>() {
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
        val feed = getItem(position) ?: return
        holder.bindFeedAuthor(feed.author)
        holder.bindFeedContent(feed.feedsText)
        holder.bindFeedImage(feed.cover)
        holder.bindFeedComment(feed.topComment)
        holder.bindFeedLabel(feed.activityText)
        holder.bindFeedInteraction(feed.ugc)
    }

    //
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        // 这里没用ViewBinding
        val layoutResId = if (viewType == TYPE_IMAGE_TEXT) R.layout.layout_feed_type_text_image else R.layout.layout_feed_type_video
        return FeedViewHolder(LayoutInflater.from(parent.context).inflate(layoutResId,parent,false))
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
                interactionBinding.interactionDiss.setIconResource(if (hasdiss) R.drawable.icon_cell_dissed else R.drawable.icon_cell_diss)
            }
        }
    }

}