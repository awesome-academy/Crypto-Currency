package com.example.cryptocurency.ui.asset_fragment

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.AssetRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class AssetFragmentPresenter(
    private val mView: AssetFragmentContract.View,
    private val assetRepository: AssetRepository
) : AssetFragmentContract.Presenter {

    override fun getLocalAssets() {
        assetRepository.getAllAsset(object : OnResultListener<MutableList<Asset>> {
            override fun onSuccess(data: MutableList<Asset>) {
                mView.getLocalAssetsSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.getLocalAssetsFailure(exception)
            }
        })
    }

    override fun getAssetsCoinData(coinIds: MutableList<String>) {
        if (coinIds.isEmpty()){
            mView.getCoinsDataSuccessfully(mutableListOf<Coin>())
            return
        }
        assetRepository.getAssetCoinData(coinIds, object : OnResultListener<MutableList<Coin>> {
            override fun onSuccess(data: MutableList<Coin>) {
                mView.getCoinsDataSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.getLocalAssetsFailure(exception)
            }
        })
    }

    override fun removeAsset(asset: Asset) {
        assetRepository.deleteAsset(asset.id, object : OnResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView.removeAssetSuccessfully(asset)
            }

            override fun onError(exception: Exception?) {
                mView.removeAssetFailure(exception)
            }
        })
    }
}
