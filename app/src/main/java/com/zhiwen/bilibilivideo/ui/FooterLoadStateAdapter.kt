package com.zhiwen.bilibilivideo.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.LayoutAbsListLoadingFooterBinding

class FooterLoadStateAdapter:LoadStateAdapter<FooterLoadStateAdapter.LoadStateViewHolder>() {

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        val text = holder.binding.text
        val loading = holder.binding.loading
        when (loadState) {
            is LoadState.Loading -> {
                text.setText(R.string.abs_list_loading_footer_loading)
                loading.show()
            }
            is LoadState.Error -> {
                text.setText(R.string.abs_list_loading_footer_error)
                loading.hide()
                loading.postOnAnimation { loading.visibility = View.GONE }
            }
            else -> {
                loading.hide()
                loading.postOnAnimation { loading.visibility = View.GONE }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        val inflate = LayoutAbsListLoadingFooterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LoadStateViewHolder(inflate)
    }

    class LoadStateViewHolder(var binding: LayoutAbsListLoadingFooterBinding):RecyclerView.ViewHolder(binding.root)
}