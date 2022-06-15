package com.example.cryptocurency.ui.asset_fragment

import android.content.Intent
import com.example.cryptocurency.R
import com.example.cryptocurency.databinding.FragmentAssetBinding
import com.example.cryptocurency.ui.add_asset.AddAssetActivity
import com.example.cryptocurency.ui.search.SearchActivity
import com.example.cryptocurency.utils.base.BaseFragment

class AssetFragment : BaseFragment<FragmentAssetBinding>(FragmentAssetBinding::inflate) {

    override fun initView() {
        binding?.apply {
            toolbar.apply {
                inflateMenu(R.menu.menu_toolbar_asset)
                setOnMenuItemClickListener {
                    handleToolBarItemClick(it.itemId)
                    true
                }
            }
        }
    }

    override fun initData() {
    }

    private fun handleToolBarItemClick(id: Int) {
        when (id) {
            R.id.nav_add -> {
                Intent(context, AddAssetActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AssetFragment()
    }
}
