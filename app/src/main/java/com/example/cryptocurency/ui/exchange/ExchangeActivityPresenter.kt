package com.example.cryptocurency.ui.exchange

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class ExchangeActivityPresenter(
    private val mView: ExchangeActivityContract.View,
    private val coinRepository: CoinRepository
) : ExchangeActivityContract.Presenter {

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
}
