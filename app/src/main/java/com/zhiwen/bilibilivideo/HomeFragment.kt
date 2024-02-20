package com.zhiwen.bilibilivideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.fragment.findNavController
import com.zhiwen.bilibilivideo.databinding.LayoutFragmentHomeBinding


class HomeFragment:BaseFragment() {

    private lateinit var homeBinding: LayoutFragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeBinding = LayoutFragmentHomeBinding.inflate(inflater)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeBinding.btnNavTags.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_tagsFragment)
//            val fm: FragmentManager = parentFragmentManager
//
//            val ft: FragmentTransaction = fm.beginTransaction()
//
//            ft.replace(R.id.fragment_container_view, TagsFragment())
//
//            ft.commit()
        }
    }
}