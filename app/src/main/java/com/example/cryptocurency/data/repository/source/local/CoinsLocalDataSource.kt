package com.example.cryptocurency.data.repository.source.local

import android.os.Handler
import android.os.Looper
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.source.CoinsDataSource
import com.example.cryptocurency.data.repository.source.OnResultListener
import com.example.cryptocurency.data.repository.source.local.database.favorite.IFavoriteCoinsDAO
import java.lang.Exception
import java.util.concurrent.Executors

class CoinsLocalDataSource(private val dao: IFavoriteCoinsDAO) : CoinsDataSource.Local {

    override fun getLocalCoins(listener: OnResultListener<MutableList<Coin>>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val data = dao.getAllFavoriteCoins().toMutableList()
                Handler(Looper.getMainLooper()).post { listener.onSuccess(data) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun insertCoin(coin: Coin, listener: OnResultListener<Unit>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                dao.insertCoin(coin)
                Handler(Looper.getMainLooper()).post { listener.onSuccess(Unit) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun deleteCoin(coinId: String, listener: OnResultListener<Unit>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                dao.deleteCoin(coinId)
                Handler(Looper.getMainLooper()).post { listener.onSuccess(Unit) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun updateLocalFavoriteCoins(
        coins: MutableList<Coin>,
        listener: OnResultListener<Unit>
    ) {
        Executors.newSingleThreadExecutor().execute {
            try {
                for (i in 0 until coins.size){
                    dao.updateCoin(coins[i])
                }
                Handler(Looper.getMainLooper()).post { listener.onSuccess(Unit) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    override fun isFavorite(coinId: String, listener: OnResultListener<Boolean>) {
        Executors.newSingleThreadExecutor().execute {
            try {
                val data = dao.isFavorite(coinId)
                Handler(Looper.getMainLooper()).post { listener.onSuccess(data) }
            } catch (e: Exception) {
                Handler(Looper.getMainLooper()).post { listener.onError(e) }
            }
        }
    }

    companion object {
        private var instance: CoinsLocalDataSource? = null

        fun getInstance(dao: IFavoriteCoinsDAO) = synchronized(this) {
            instance ?: CoinsLocalDataSource(dao).also { instance = it }
        }
    }
}
