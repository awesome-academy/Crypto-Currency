package com.example.cryptocurency.ui.favorite_fragment

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.FragmentFavoriteBinding
import com.example.cryptocurency.ui.exchange.ExchangeActivity
import com.example.cryptocurency.ui.adapter.ItemClickListener
import com.example.cryptocurency.ui.adapter.MainCoinAdapter
import com.example.cryptocurency.ui.detail.DetailActivity
import com.example.cryptocurency.utils.COIN_EXTRA
import com.example.cryptocurency.utils.EXCEPTION_NO_DATA
import com.example.cryptocurency.utils.base.BaseFragment
import com.example.cryptocurency.utils.extension.showToast
import com.example.cryptocurency.utils.factory.PresenterFactory
import com.example.cryptocurency.utils.factory.SpinnerFactory

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(FragmentFavoriteBinding::inflate),
    FavoriteFragmentContract.View, ItemClickListener<Coin> {

    private var mPresenter: FavoriteFragmentContract.Presenter? = null
    private var coinsAdapter = MainCoinAdapter(this)
    private var currentSort: String? = null
    private var isUpdate: Boolean = false

    override fun initView() {
        binding?.apply {
            toolbar.apply {
                inflateMenu(R.menu.menu_toolbar_home)
                setOnMenuItemClickListener {
                    handleToolBarItemClick(it.itemId)
                    true
                }
            }
            sortSpinner.apply {
                adapter = SpinnerFactory.createSpinnerAdapter(context, R.array.coin_catalogs)
                onItemSelectedListener = SpinnerFactory.createSpinnerListener(
                    {
                        currentSort = resources.getStringArray(R.array.coin_catalogs)[it]
                        handleSortChanged()
                    },
                    {}
                )
            }
            recyclerviewCoin.apply {
                this.adapter = coinsAdapter
            }
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(context).createFavoriteFragmentPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.getFavoriteList()
    }

    override fun getFavoriteSuccessfully(list: MutableList<Coin>) {
        coinsAdapter.setAdapterData(list)
        handleSortChanged()
        binding?.apply {
            if (list.isNotEmpty()) {
                txtError.visibility = View.GONE
                recyclerviewCoin.visibility = View.VISIBLE
                if (!isUpdate) {
                    mPresenter?.updateFavoriteListData(list)
                }
            } else {
                getFavoriteFailure(Exception(EXCEPTION_NO_DATA))
            }
        }
    }

    override fun getFavoriteFailure(exception: Exception?) {
        binding?.apply {
            recyclerviewCoin.visibility = View.GONE
            txtError.apply {
                text = exception?.message
                visibility = View.VISIBLE
            }
        }
    }

    override fun updateFavoriteSuccessfully(list: MutableList<Coin>) {
        coinsAdapter.updateAdapterData(list)
        isUpdate = true
    }

    override fun updateFavoriteFailure(exception: Exception?) {
        context?.showToast(exception?.message)
    }

    override fun removeFavoriteCoinSuccessfully(coin: Coin) {
        coinsAdapter.deleteAdapterData(coin)
    }

    override fun removeFavoriteCoinFailure(exception: Exception?) {
        context?.showToast(exception?.message)
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
        item?.let { coin ->
            val confirmTitle = "Remove Favorite"
            val confirmMessage = "Are you sure you want to remove ${coin.name} from favorite?"
            val negativeText ="No"
            val positiveText = "Yes"
            context?.apply {
                AlertDialog.Builder(this).apply {
                    setTitle(confirmTitle)
                    setMessage(confirmMessage)
                    setCancelable(false)
                    setPositiveButton(positiveText) { _, _ ->
                        mPresenter?.removeFavoriteCoin(coin)
                    }
                    setNegativeButton(negativeText) { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
        }
        return true
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

    private fun handleSortChanged() {
        currentSort?.let {
            coinsAdapter.sortAdapterData(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FavoriteFragment()
    }
}
