package com.example.cryptocurency.ui.asset_fragment

import com.example.cryptocurency.databinding.FragmentAssetBinding
import com.example.cryptocurency.utils.base.BaseFragment

class AssetFragment : BaseFragment<FragmentAssetBinding>(FragmentAssetBinding::inflate) {

    override fun initView() {
    }

    override fun initData() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = AssetFragment()
    }
}
