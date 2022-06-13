package com.example.cryptocurency.data.repository.source

import com.example.cryptocurency.data.models.Coin

interface CoinsDataSource {
    interface Local {
        fun getLocalCoins(listener: OnResultListener<MutableList<Coin>>)
        fun insertCoin(coin: Coin, listener: OnResultListener<Unit>)
        fun deleteCoin(coinId: String, listener: OnResultListener<Unit>)
        fun isFavorite(coinId: String, listener: OnResultListener<Boolean>)
    }

    interface Remote {
        fun getAllRemoteCoins(listener: OnResultListener<MutableList<Coin>>)
        fun getRemoteCoinsByScope(
            limit: Int,
            orderBy: String,
            listener: OnResultListener<MutableList<Coin>>
        )

        fun searchCoin(query: String, listener: OnResultListener<MutableList<Coin>>)
        fun searchCoinWithLimit(
            query: String,
            limit: Int,
            listener: OnResultListener<MutableList<Coin>>
        )

        fun getCoinDetail(coinId: String, listener: OnResultListener<Coin>)
    }
}
