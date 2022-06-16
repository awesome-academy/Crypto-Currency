package com.example.cryptocurency.ui.asset_fragment

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin

interface AssetFragmentContract {

    interface View{
        fun getLocalAssetsSuccessfully(assets: MutableList<Asset>)
        fun getLocalAssetsFailure(exception: Exception?)
        fun getCoinsDataSuccessfully(coins: MutableList<Coin>)
        fun getCoinsDataFailure(exception: Exception?)
        fun removeAssetSuccessfully(asset: Asset)
        fun removeAssetFailure(exception: Exception?)
    }

    interface Presenter{
        fun getLocalAssets()
        fun getAssetsCoinData(coinIds: MutableList<String>)
        fun removeAsset(asset: Asset)
    }
}
