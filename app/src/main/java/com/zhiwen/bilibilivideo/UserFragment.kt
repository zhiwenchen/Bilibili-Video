package com.zhiwen.bilibilivideo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.zhiwen.bilibilivideo.databinding.LayoutFragmentUserBinding

class UserFragment:BaseFragment() {

    private lateinit var userBinding: LayoutFragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userBinding = LayoutFragmentUserBinding.inflate(inflater)
        return userBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userBinding.btnNavHome.setOnClickListener {
//            findNavController().navi
        }
    }
}