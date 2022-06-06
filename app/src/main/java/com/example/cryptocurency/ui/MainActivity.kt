package com.example.cryptocurency.ui

import androidx.viewpager2.widget.ViewPager2
import com.example.cryptocurency.databinding.ActivityMainBinding
import com.example.cryptocurency.ui.adapter.TabLayoutAdapter
import com.example.cryptocurency.utils.base.BaseActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    override fun initData() {
    }

    override fun initView() {
        setUpTabBar()
    }

    private fun setUpTabBar() {
        binding?.apply{
            val count = tabLayout.tabCount
            TabLayoutAdapter(this@MainActivity, count).apply {
                viewPager.adapter = this
            }
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
                }
            })
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewPager.currentItem = tab?.position ?: 0
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
    }
}
