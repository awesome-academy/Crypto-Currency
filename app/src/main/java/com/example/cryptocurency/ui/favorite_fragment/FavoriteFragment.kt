package com.example.cryptocurency.ui.favorite_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cryptocurency.R
import com.example.cryptocurency.databinding.FragmentFavoriteBinding
import com.example.cryptocurency.databinding.FragmentHomeBinding
import com.example.cryptocurency.utils.base.BaseFragment

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate) {

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
