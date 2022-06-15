package com.example.cryptocurency.data.repository.source

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin

interface AssetDataSource {

    interface Local{
        fun getAllAsset(listener: OnResultListener<MutableList<Asset>>)
        fun insertAsset(asset: Asset, listener: OnResultListener<Unit>)
        fun deleteAsset(assetId: Int,  listener: OnResultListener<Unit>)
        fun updateAsset(asset: Asset, listener: OnResultListener<Unit>)
    }

    interface Remote{
        fun getAssetCoinData(list:MutableList<String>, listener: OnResultListener<MutableList<Coin>>)
    }
}
