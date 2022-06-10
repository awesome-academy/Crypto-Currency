package com.example.cryptocurency.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.cryptocurency.ui.asset_fragment.AssetFragment
import com.example.cryptocurency.ui.favorite_fragment.FavoriteFragment
import com.example.cryptocurency.ui.home_fragment.HomeFragment

class TabLayoutAdapter(
    activity: FragmentActivity,
    private val tabCount: Int
) : FragmentStateAdapter(activity) {

    private val TAB_HOME = 0
    private val TAB_FAVORITE = 1
    private val TAB_ASSET = 2

    override fun getItemCount(): Int = tabCount

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TAB_HOME -> HomeFragment.newInstance()
            TAB_FAVORITE -> FavoriteFragment.newInstance()
            TAB_ASSET -> AssetFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }
}
