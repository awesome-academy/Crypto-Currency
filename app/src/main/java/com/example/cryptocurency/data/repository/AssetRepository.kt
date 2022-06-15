package com.example.cryptocurency.data.repository

import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.source.AssetDataSource
import com.example.cryptocurency.data.repository.source.CoinsDataSource
import com.example.cryptocurency.data.repository.source.OnResultListener

class AssetRepository(
    private val remote: AssetDataSource.Remote,
    private val local: AssetDataSource.Local,
) : AssetDataSource.Local, AssetDataSource.Remote {

    override fun getAllAsset(listener: OnResultListener<MutableList<Asset>>) {
        local.getAllAsset(listener)
    }

    override fun insertAsset(asset: Asset, listener: OnResultListener<Unit>) {
        local.insertAsset(asset, listener)
    }

    override fun deleteAsset(assetId: Int, listener: OnResultListener<Unit>) {
        local.deleteAsset(assetId, listener)
    }

    override fun updateAsset(asset: Asset, listener: OnResultListener<Unit>) {
        local.updateAsset(asset, listener)
    }

    override fun getAssetCoinData(
        list: MutableList<String>,
        listener: OnResultListener<MutableList<Coin>>
    ) {
        remote.getAssetCoinData(list, listener)
    }

    companion object {
        private var instance: AssetRepository? = null

        fun getInstance(remote: AssetDataSource.Remote, local: AssetDataSource.Local) =
            synchronized(this) {
                instance ?: AssetRepository(remote, local).also { instance = it }
            }
    }
}
