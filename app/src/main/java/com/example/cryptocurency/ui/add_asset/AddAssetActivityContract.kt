package com.example.cryptocurency.ui.add_asset

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin

interface AddAssetActivityContract {

    interface View{
        fun getCoinsDataSuccessfully(coins: MutableList<Coin>)
        fun getCoinsDataFailure(exception: Exception?)
        fun addAssetSuccessfully()
        fun addAssetFailure(exception: Exception?)
    }

    interface Presenter{
        fun getCoinsData()
        fun addAsset(asset: Asset)
    }
}
