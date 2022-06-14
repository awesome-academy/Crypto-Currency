package com.example.cryptocurency.data.repository.source.remote

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.source.CoinsDataSource
import com.example.cryptocurency.data.repository.source.OnResultListener
import com.example.cryptocurency.data.repository.source.remote.connection.ApiManager
import com.example.cryptocurency.data.repository.source.remote.connection.HttpConnection

class CoinsRemoteDataSource : CoinsDataSource.Remote {

    override fun getAllRemoteCoins(listener: OnResultListener<MutableList<Coin>>) {
        HttpConnection.callApiCoins(ApiManager.getCoinsUrl(), listener)
    }

    override fun getRemoteCoinsByScope(
        limit: Int,
        orderBy: String,
        listener: OnResultListener<MutableList<Coin>>
    ) {
        HttpConnection.callApiCoins(ApiManager.getCoinsWithScopeUrl(limit, orderBy), listener)
    }

    override fun searchCoin(query: String, listener: OnResultListener<MutableList<Coin>>) {
        HttpConnection.callApiCoins(ApiManager.getSearchUrl(query), listener)
    }

    override fun searchCoinWithLimit(
        query: String,
        limit: Int,
        listener: OnResultListener<MutableList<Coin>>
    ) {
        HttpConnection.callApiCoins(ApiManager.getSearchWithLimitUrl(query, limit), listener)
    }

    override fun getCoinDetail(coinId: String, listener: OnResultListener<Coin>) {
        HttpConnection.callApiDetailCoin(ApiManager.getCoinDetailUrl(coinId), listener)
    }

    override fun updateListCoins(list:MutableList<String>, listener: OnResultListener<MutableList<Coin>>) {
        HttpConnection.callApiCoins(ApiManager.getCoinsByIdUrl(list),listener)
    }

    companion object {
        private var instance: CoinsRemoteDataSource? = null

        fun getInstance() = synchronized(this) {
            instance ?: CoinsRemoteDataSource().also { instance = it }
        }
    }
}
