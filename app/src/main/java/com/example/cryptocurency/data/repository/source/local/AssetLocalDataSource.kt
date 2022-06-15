package com.example.cryptocurency.data.repository.source.local

import android.os.Handler
import android.os.Looper
import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.repository.source.AssetDataSource
import com.example.cryptocurency.data.repository.source.OnResultListener
import com.example.cryptocurency.data.repository.source.local.database.asset.IAssetDAO
import com.example.cryptocurency.data.repository.source.local.database.favorite.IFavoriteCoinsDAO
import java.lang.Exception
import java.util.concurrent.Executors

class AssetLocalDataSource(private val dao: IAssetDAO) : AssetDataSource.Local {

    override fun getAllAsset(listener: OnResultListener<MutableList<Asset>>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val data = dao.getAllAsset().toMutableList()
                Handler(Looper.getMainLooper()).post { listener.onSuccess(data) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun insertAsset(asset: Asset, listener: OnResultListener<Unit>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                dao.insertAsset(asset)
                Handler(Looper.getMainLooper()).post { listener.onSuccess(Unit) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun deleteAsset(assetId: Int, listener: OnResultListener<Unit>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                dao.deleteAsset(assetId)
                Handler(Looper.getMainLooper()).post { listener.onSuccess(Unit) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun updateAsset(asset: Asset, listener: OnResultListener<Unit>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                dao.updateAsset(asset)
                Handler(Looper.getMainLooper()).post { listener.onSuccess(Unit) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    companion object {
        private var instance: AssetLocalDataSource? = null

        fun getInstance(dao: IAssetDAO) = synchronized(this) {
            instance ?: AssetLocalDataSource(dao).also { instance = it }
        }
    }
}
