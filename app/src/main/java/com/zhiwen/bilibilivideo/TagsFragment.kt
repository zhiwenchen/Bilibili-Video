package com.zhiwen.bilibilivideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zhiwen.bilibilivideo.databinding.LayoutFragmentTagsBinding

class TagsFragment:BaseFragment() {

    private lateinit var tagsBinding: LayoutFragmentTagsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        tagsBinding = LayoutFragmentTagsBinding.inflate(inflater)
        return tagsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tagsBinding.btnNavUser.setOnClickListener {
            findNavController().navigate(R.id.action_tagsFragment_to_userFragment)
        }
    }
}