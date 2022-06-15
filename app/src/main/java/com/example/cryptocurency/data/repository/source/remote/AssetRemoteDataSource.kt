package com.example.cryptocurency.data.repository.source.remote

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.source.AssetDataSource
import com.example.cryptocurency.data.repository.source.OnResultListener
import com.example.cryptocurency.data.repository.source.remote.connection.ApiManager
import com.example.cryptocurency.data.repository.source.remote.connection.HttpConnection

class AssetRemoteDataSource: AssetDataSource.Remote {

    override fun getAssetCoinData(
        list: MutableList<String>,
        listener: OnResultListener<MutableList<Coin>>
    ) {
        HttpConnection.callApiCoins(ApiManager.getCoinsByIdUrl(list),listener)
    }

    companion object {
        private var instance: AssetRemoteDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: AssetRemoteDataSource().also { instance = it }
        }
    }
}
