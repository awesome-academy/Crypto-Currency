package com.example.cryptocurency.ui.asset_fragment

import android.content.Intent
import androidx.appcompat.app.AlertDialog
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.FragmentAssetBinding
import com.example.cryptocurency.ui.adapter.ItemClickListener
import com.example.cryptocurency.ui.adapter.MyAssetAdapter
import com.example.cryptocurency.ui.add_asset.AddAssetActivity
import com.example.cryptocurency.ui.detail.DetailActivity
import com.example.cryptocurency.utils.COIN_EXTRA
import com.example.cryptocurency.utils.PERCENT_NUM
import com.example.cryptocurency.utils.base.BaseFragment
import com.example.cryptocurency.utils.extension.*
import com.example.cryptocurency.utils.factory.PresenterFactory

class AssetFragment : BaseFragment<FragmentAssetBinding>(FragmentAssetBinding::inflate),
    AssetFragmentContract.View, ItemClickListener<Asset> {

    private var mPresenter: AssetFragmentContract.Presenter? = null
    private var mWallet = mutableListOf<Asset>()
    private var assetCoinsData = mutableListOf<Coin>()
    private val assetAdapter = MyAssetAdapter(this)


    override fun initView() {
        binding?.apply {
            toolbar.apply {
                inflateMenu(R.menu.menu_toolbar_asset)
                setOnMenuItemClickListener {
                    handleToolBarItemClick(it.itemId)
                    true
                }
            }
            recyclerviewAssets.adapter = assetAdapter
        }
    }

    override fun initData() {
        mPresenter = PresenterFactory(context).createAssetFragmentPresenter(this)
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.getLocalAssets()
    }

    override fun getLocalAssetsSuccessfully(assets: MutableList<Asset>) {
        assets.let {
            mWallet = it
            assetAdapter.setAdapterData(it)
            mPresenter?.getAssetsCoinData(getAssetsCoinId(mWallet, false))
        }
    }

    override fun getLocalAssetsFailure(exception: Exception?) {
        context?.showToast(exception?.message)
    }

    override fun getCoinsDataSuccessfully(coins: MutableList<Coin>) {
        assetAdapter.updateAdapterData(coins)
        mWallet = assetAdapter.getAssets()
        updateTotal()
        updateAssetCoinData(coins)
    }

    override fun getCoinsDataFailure(exception: Exception?) {
        context?.showToast(exception?.message)
        assetAdapter.updateAdapterData(assetCoinsData)
        mWallet = assetAdapter.getAssets()
        updateTotal()
        updateAssetCoinData(assetCoinsData)
    }

    override fun removeAssetSuccessfully(asset: Asset) {
        assetAdapter.deleteAdapterData(asset)
        mWallet = assetAdapter.getAssets()
        updateTotal()
    }

    override fun removeAssetFailure(exception: Exception?) {
        context?.showToast(exception?.message)
    }

    override fun onItemClick(item: Asset?) {
        item?.apply {
            assetCoinsData.firstOrNull {
                it.id == coinId
            }?.let {
                Intent(context, DetailActivity::class.java).apply {
                    putExtra(COIN_EXTRA, it)
                    startActivity(this)
                }
            }
        }
    }

    override fun onItemLongClick(item: Asset?): Boolean {
        item?.let { asset ->
            val confirmTitle = "Delete Asset"
            val confirmMessage = "Are you sure you want to delete this asset?"
            context?.showConfirmDialog(confirmTitle, confirmMessage) {
                mPresenter?.removeAsset(asset)
            }
        }
        return true
    }

    private fun updateTotal() {
        var totalPurchase = 0.0
        var totalCurrent = 0.0
        mWallet.forEach { asset ->
            asset.purchasePrice.toDoubleOrNull()?.let {
                totalPurchase += it
            }
            asset.currentPrice?.toDoubleOrNull()?.let {
                totalCurrent += it
            }
        }
        val change = (totalCurrent - totalPurchase).roundInt()
        val changePercent = ((change / totalPurchase)* PERCENT_NUM).roundInt()
        binding?.apply {
            txtCurrentBalance.text = totalCurrent.toString().getCoinPrice()
            txtWinLossNum.text = change.toString().getCoinPrice()
            txtWinLossPercent.text = changePercent.toString().getCoinChange()
            txtWinLossNum.setTextColorWithPrice(change)
            txtWinLossPercent.setTextColorWithPrice(change)
        }
    }

    private fun getAssetsCoinId(assets: MutableList<Asset>, isAll: Boolean): MutableList<String> {
        val coinIds = mutableListOf<String>()
        assets.forEach {
            if (it.currentPrice.isNullOrEmpty() || isAll) coinIds.add(it.coinId)
        }
        return coinIds
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

    private fun updateAssetCoinData(coins: MutableList<Coin>) {
        coins.forEach { coin ->
            val check = assetCoinsData.firstOrNull {
                coin.id == it.id
            }
            if (check == null) {
                assetCoinsData.add(coin)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AssetFragment()
    }
}
