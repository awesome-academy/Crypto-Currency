package com.example.cryptocurency.ui.home_fragment

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.FragmentHomeBinding
import com.example.cryptocurency.ui.detail.DetailActivity
import com.example.cryptocurency.ui.ExchangeActivity
import com.example.cryptocurency.ui.adapter.ItemClickListener
import com.example.cryptocurency.ui.adapter.MainCoinAdapter
import com.example.cryptocurency.utils.COIN_EXTRA
import com.example.cryptocurency.utils.EXCEPTION_NO_DATA
import com.example.cryptocurency.utils.factory.PresenterFactory
import com.example.cryptocurency.utils.base.BaseFragment
import com.example.cryptocurency.utils.extension.setTextColorFromResource
import com.example.cryptocurency.utils.extension.showToast
import java.lang.Exception

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    HomeFragmentContract.View, ItemClickListener<Coin> {

    private var mPresenter: HomeFragmentContract.Presenter? = null
    private var coinsAdapter = MainCoinAdapter(this)
    private var currentSort: String? = null
    private var currentLimit: Int? = null

    override fun initView() {
        context?.let { context ->
            binding?.apply {
                toolBar.apply {
                    inflateMenu(R.menu.menu_toolbar_home)
                    setOnMenuItemClickListener {
                        handleToolBarItemClick(it.itemId)
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
                            currentSort = resources.getStringArray(R.array.coin_catalogs)[it]
                            handleGetCoin()
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
                            currentLimit =
                                resources.getStringArray(R.array.coin_limits)[it].toIntOrNull()
                            handleGetCoin()
                        },
                        {}
                    )
                }
                recyclerviewCoin.apply {
                    this.adapter = coinsAdapter
                }
            }
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(context).createHomeFragmentPresenter(this)
    }

    override fun getCoinSuccessfully(coins: MutableList<Coin>) {
        updateCoinsData(coins)
        binding?.apply {
            recyclerviewCoin.visibility = View.VISIBLE
            if(coins.isEmpty()){
                context?.showToast(EXCEPTION_NO_DATA)
            }
        }
    }

    override fun getCoinFailed(exception: Exception?) {
        binding?.apply {
            txtError.apply{
                visibility = View.VISIBLE
                text = exception?.message
            }
        }
    }

    override fun displayLoading() {
        binding?.apply {
            recyclerviewCoin.visibility = View.GONE
            txtError.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            showSpinner.isEnabled = false
            topSpinner.isEnabled = false
        }
    }

    override fun hideLoading() {
        binding?.apply {
            progressBar.visibility = View.GONE
            showSpinner.isEnabled = true
            topSpinner.isEnabled = true
        }
    }

    private fun createSpinnerListener(
        context: Context,
        handleSelect: (Int) -> Unit,
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
            handleSelect(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            handleNoSelect()
        }
    }

    override fun onItemClick(item: Coin?) {
        item?.let {
            Intent(context, DetailActivity::class.java).apply {
                putExtra(COIN_EXTRA, item)
                startActivity(this)
            }
        }
    }

    override fun onItemLongClick(item: Coin?): Boolean {
        return true
    }

    private fun updateCoinsData(coins: MutableList<Coin>) {
        coinsAdapter.setAdapterData(coins)
    }

    private fun handleGetCoin() {
        currentLimit?.let { limit ->
            currentSort?.let { sort ->
                mPresenter?.getCoin(limit, sort)
            }
        }
    }

    private fun handleToolBarItemClick(id: Int) {
        when (id) {
            R.id.nav_search -> {}
            R.id.nav_exchange -> {
                Intent(context, ExchangeActivity::class.java).apply {
                    startActivity(this)
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
