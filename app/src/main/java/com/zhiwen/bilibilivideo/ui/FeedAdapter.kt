package com.zhiwen.bilibilivideo.ui

import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.zhiwen.bilibilivideo.model.Feed

class FeedAdapter:PagingDataAdapter<Feed,FeedAdapter.FeedViewHolder>(object: DiffUtil.ItemCallback<Feed>() {
    override fun areItemsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem.itemId == newItem.itemId
    }

    override fun areContentsTheSame(oldItem: Feed, newItem: Feed): Boolean {
        return oldItem == newItem
    }

}) {

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        TODO("Not yet implemented")
    }

    inner class FeedViewHolder(itemView: View):ViewHolder(itemView){

    }

}