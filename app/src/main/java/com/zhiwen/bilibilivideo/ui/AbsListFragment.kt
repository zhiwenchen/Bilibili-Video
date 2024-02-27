package com.zhiwen.bilibilivideo.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zhiwen.bilibilivideo.R
import com.zhiwen.bilibilivideo.databinding.LayoutAbsListFragmentBinding
import com.zhiwen.bilibilivideo.ext.invokeViewBinding

// 首页
class AbsListFragment : Fragment(R.layout.layout_abs_list_fragment) {

    private val viewBinding: LayoutAbsListFragmentBinding by invokeViewBinding()


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
        val feedAdapter = FeedAdapter()
        viewBinding.listView.adapter = feedAdapter.withLoadStateFooter()


    }

}