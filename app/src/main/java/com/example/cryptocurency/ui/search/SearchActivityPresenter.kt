package com.example.cryptocurency.ui.search

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class SearchActivityPresenter(
    private val mView: SearchActivityContract.View,
    private val coinRepository: CoinRepository
) : SearchActivityContract.Presenter {

    override fun getCoinsData() {
        coinRepository.getAllRemoteCoins(object : OnResultListener<MutableList<Coin>> {
            override fun onSuccess(data: MutableList<Coin>) {
                mView.getCoinsDataSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.getCoinsDataFailure(exception)
            }
        })
    }

    override fun searchCoins(query: String) {
        coinRepository.searchCoin(query, object : OnResultListener<MutableList<Coin>> {
            override fun onSuccess(data: MutableList<Coin>) {
                mView.searchCoinsSuccessfully(data)
            }

            override fun onError(exception: Exception?) {
                mView.searchCoinsFailure(exception)
            }
        })
    }
}
