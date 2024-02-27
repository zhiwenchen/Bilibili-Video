package com.zhiwen.bilibilivideo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.*
import com.zhiwen.bilibilivideo.ext.setTextVisibility
import com.zhiwen.bilibilivideo.model.Feed
import com.zhiwen.bilibilivideo.model.TYPE_IMAGE_TEXT

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
        holder.bindFeedContent(feed.feedsText)
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

        fun bindFeedContent(feedsText: String?) {
            feedTextBinding.root.setTextVisibility(feedsText)
        }
    }

}