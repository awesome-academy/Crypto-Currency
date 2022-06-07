package com.example.cryptocurency.data.repository.source.local.database.asset

import com.example.cryptocurency.data.models.Asset

interface IAssetDAO {
    fun getAllAsset() : List<Asset>
    fun insertAsset(asset: Asset)
    fun updateAsset(asset: Asset)
    fun deleteAsset(assetId: Int)
}
