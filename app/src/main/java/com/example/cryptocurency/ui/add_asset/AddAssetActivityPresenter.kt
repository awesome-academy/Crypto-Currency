package com.example.cryptocurency.ui.add_asset

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.AssetRepository
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class AddAssetActivityPresenter(
    private val mView: AddAssetActivityContract.View,
    private val coinRepository: CoinRepository,
    private val assetRepository: AssetRepository,
) : AddAssetActivityContract.Presenter {

    override fun getCoinsData() {
        coinRepository.getAllRemoteCoins(object : OnResultListener<MutableList<Coin>> {
            override fun onSuccess(data: MutableList<Coin>) {
                mView.getCoinsDataSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.getCoinsDataFailure(exception)
            }
        })
    }

    override fun addAsset(asset: Asset) {
        assetRepository.insertAsset(asset,object : OnResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView.addAssetSuccessfully()
            }

            override fun onError(exception: Exception?) {
                mView.addAssetFailure(exception)
            }
        })
    }
}
