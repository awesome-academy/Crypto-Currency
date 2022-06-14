package com.example.cryptocurency.ui.home_fragment

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.repository.CoinRepository
import com.example.cryptocurency.data.repository.source.OnResultListener

class HomeFragmentPresenter(
    private val mView: HomeFragmentContract.View,
    private val coinRepository: CoinRepository
) : HomeFragmentContract.Presenter {

    override fun getCoin(limit: Int, orderBy: String) {
        mView.apply {
            displayLoading()
            coinRepository.getRemoteCoinsByScope(
                orderBy = orderBy,
                limit = limit,
                listener = object : OnResultListener<MutableList<Coin>> {
                    override fun onSuccess(data: MutableList<Coin>) {
                        hideLoading()
                        getCoinSuccessfully(data)
                    }

                    override fun onError(exception: Exception?) {
                        hideLoading()
                        getCoinFailed(exception)
                    }
                }
            )
        }
    }
}
