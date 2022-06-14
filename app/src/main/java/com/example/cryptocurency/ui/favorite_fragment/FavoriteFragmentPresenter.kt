package com.example.cryptocurency.ui.favorite_fragment

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class FavoriteFragmentPresenter(
    private val mView: FavoriteFragmentContract.View,
    private val coinRepository: CoinRepository
) : FavoriteFragmentContract.Presenter {

    override fun getFavoriteList() {
        coinRepository.getLocalCoins(object : OnResultListener<MutableList<Coin>> {
            override fun onSuccess(data: MutableList<Coin>) {
                mView.getFavoriteSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.getFavoriteFailure(exception)
            }
        })
    }

    override fun updateFavoriteListData(list: MutableList<Coin>) {
        val coinsId = mutableListOf<String>()
        list.map {
            coinsId.add(it.id)
        }
        coinRepository.updateListCoins(coinsId, object : OnResultListener<MutableList<Coin>> {
            override fun onSuccess(data: MutableList<Coin>) {
                mView.updateFavoriteSuccessfully(data)
                updateLocalCoin(data)
            }

            override fun onError(exception: Exception?) {
                mView.updateFavoriteFailure(exception)
            }
        })
    }

    override fun removeFavoriteCoin(coin: Coin) {
        coinRepository.deleteCoin(coin.id, object : OnResultListener<Unit>{
            override fun onSuccess(data: Unit) {
                mView.removeFavoriteCoinSuccessfully(coin)
            }

            override fun onError(exception: Exception?) {
                mView.removeFavoriteCoinFailure(exception)
            }
        })
    }

    private fun updateLocalCoin(list: MutableList<Coin>) {
        coinRepository.updateLocalFavoriteCoins(list, object : OnResultListener<Unit> {
            override fun onSuccess(data: Unit) {
            }

            override fun onError(exception: Exception?) {
                mView.updateFavoriteFailure(exception)
            }
        })
    }
}
