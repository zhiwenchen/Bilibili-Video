package com.zhiwen.bilibilivideo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.LayoutAbsListFragmentBinding
import com.zhiwen.bilibilivideo.ext.invokeViewBinding
import com.zhiwen.bilibilivideo.model.Feed
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// 首页
open class AbsListFragment : Fragment() {

    private val viewBinding: LayoutAbsListFragmentBinding by invokeViewBinding()
    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        val context = requireContext()
        viewBinding.listView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        viewBinding.listView.addItemDecoration(DividerItemDecoration(context,DividerItemDecoration.VERTICAL))
        feedAdapter = FeedAdapter()
        viewBinding.listView.adapter = feedAdapter.withLoadStateFooter(FooterLoadStateAdapter())
        viewBinding.refreshLayout.setOnRefreshListener {
            lifecycleScope.launch {
                feedAdapter.refresh()
            }
        }

        lifecycleScope.launch{
            feedAdapter.onPagesUpdatedFlow.collect{
                if (feedAdapter.itemCount > 0) {
                    viewBinding.refreshLayout.isRefreshing = false
                }
            }
        }

    }

    suspend fun submitData(pagingData: PagingData<Feed>) {
        feedAdapter.submitData(pagingData)
    }

}