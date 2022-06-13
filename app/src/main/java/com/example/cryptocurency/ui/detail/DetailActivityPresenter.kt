package com.example.cryptocurency.ui.detail

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class DetailActivityPresenter(
    private val mView: DetailActivityContract.View,
    private val coinRepository: CoinRepository
) : DetailActivityContract.Presenter {

    override fun getCoinDetail(coin: Coin) {
        coinRepository.getCoinDetail(coin.id, object : OnResultListener<Coin> {
            override fun onSuccess(data: Coin) {
                mView.getCoinDetailSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.getCoinDetailFailure(exception)
            }
        })
    }

    override fun checkFavorite(coin: Coin) {
        coinRepository.isFavorite(coin.id, object : OnResultListener<Boolean> {
            override fun onSuccess(data: Boolean) {
                mView.checkFavoriteSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.checkFavoriteFailure(exception)
            }
        })
    }

    override fun handleFavoriteClick(coin: Coin, isFavorite: Boolean) {
        if (isFavorite)
            removeCoinFromFavorite(coin)
        else
            insertCoinToFavorite(coin)
    }

    private fun insertCoinToFavorite(coin: Coin) {
        coinRepository.insertCoin(coin, object : OnResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView.updateFavoriteSuccessfully(true)
            }

            override fun onError(exception: Exception?) {
                mView.updateFavoriteFailure(exception)
            }
        })
    }

    private fun removeCoinFromFavorite(coin: Coin) {
        coinRepository.deleteCoin(coin.id, object : OnResultListener<Unit> {
            override fun onSuccess(data: Unit) {
                mView.updateFavoriteSuccessfully(false)
            }

            override fun onError(exception: Exception?) {
                mView.updateFavoriteFailure(exception)
            }
        })
    }
}
