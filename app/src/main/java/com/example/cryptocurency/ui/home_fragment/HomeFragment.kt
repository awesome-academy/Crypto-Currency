package com.example.cryptocurency.ui.home_fragment

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.FragmentHomeBinding
import com.example.cryptocurency.ui.adapter.ItemClickListener
import com.example.cryptocurency.ui.adapter.MainCoinAdapter
import com.example.cryptocurency.utils.base.BaseFragment
import com.example.cryptocurency.utils.extension.setTextColorFromResource
import com.example.cryptocurency.utils.extension.showToast
import java.lang.Exception

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    HomeFragmentContract.View, ItemClickListener<Coin> {

    private val mPresenter = HomeFragmentPresenter(this)
    private val mCoinList = mutableListOf<Coin>()
    private var coinsAdapter = MainCoinAdapter(this)

    override fun initView() {
        context?.let { context ->
            binding?.apply {
                toolBar.apply {
                    inflateMenu(R.menu.menu_toolbar_home)
                    setOnMenuItemClickListener {
                        context.showToast(it.title)
                        true
                    }
                }
                topSpinner.apply {
                    adapter = ArrayAdapter.createFromResource(
                        context,
                        R.array.coin_catalogs,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
                    )
                    onItemSelectedListener = createSpinnerListener(
                        context,
                        {
                            context.showToast(it)
                        },
                        {}
                    )
                }
                showSpinner.apply {
                    adapter = ArrayAdapter.createFromResource(
                        context,
                        R.array.coin_limits,
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
                    )
                    onItemSelectedListener = createSpinnerListener(
                        context,
                        {
                            context.showToast(it)
                        },
                        {}
                    )
                }
                coinsAdapter.setAdapterData(mCoinList)
                updateCoinsData()
                recyclerviewCoin.apply {
                    this.adapter = coinsAdapter
                }
            }
        }
    }

    override fun initData() {
    }

    override fun getCoinSuccessfully(coins: List<Coin>) {
    }

    override fun getCoinFailed(exception: Exception) {
    }

    override fun displayLoading() {
    }

    override fun hideLoading() {
    }

    private fun createSpinnerListener(
        context: Context,
        handleSelect: (String) -> Unit,
        handleNoSelect: () -> Unit
    ) = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            (view as TextView).setTextColorFromResource(R.color.color_white)
            handleSelect(position.toString())
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            handleNoSelect()
        }
    }

    private fun updateCoinsData() {
        mCoinList.add(Coin())
    }

    override fun onItemClick(item: Coin?) {
    }

    override fun onItemLongClick(item: Coin?): Boolean {
        return true
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
