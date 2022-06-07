package com.example.cryptocurency.data.repository

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.source.CoinsDataSource
import com.example.cryptocurency.data.repository.source.OnResultListener

class CoinRepository(
    private val remote: CoinsDataSource.Remote,
    private val local: CoinsDataSource.Local
) : CoinsDataSource.Remote, CoinsDataSource.Local {

    override fun getLocalCoins(listener: OnResultListener<MutableList<Coin>>) {
        local.getLocalCoins(listener)
    }

    override fun insertCoin(coin: Coin, listener: OnResultListener<Unit>) {
        local.insertCoin(coin, listener)
    }

    override fun deleteCoin(coinId: String, listener: OnResultListener<Unit>) {
        local.deleteCoin(coinId, listener)
    }

    override fun isFavorite(coinId: String, listener: OnResultListener<Boolean>) {
        local.isFavorite(coinId, listener)
    }

    override fun getAllRemoteCoins(listener: OnResultListener<MutableList<Coin>>) {
        remote.getAllRemoteCoins(listener)
    }

    override fun getRemoteCoinsByScope(
        scopeLimit: Int,
        scopeId: String,
        listener: OnResultListener<MutableList<Coin>>
    ) {
        remote.getAllRemoteCoins(listener)
    }

    override fun searchCoin(query: String, listener: OnResultListener<MutableList<Coin>>) {
        remote.searchCoin(query, listener)
    }

    override fun searchCoinWithLimit(
        query: String,
        limit: Int,
        listener: OnResultListener<MutableList<Coin>>
    ) {
        remote.searchCoinWithLimit(query, limit, listener)
    }

    override fun getCoinDetail(coinId: String, listener: OnResultListener<Coin>) {
        remote.getCoinDetail(coinId, listener)
    }

    companion object {
        private var instance: CoinRepository? = null

        fun getInstance(remote: CoinsDataSource.Remote, local: CoinsDataSource.Local) =
            synchronized(this) {
                instance ?: CoinRepository(remote, local).also { instance = it }
            }
    }
}
