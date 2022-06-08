package com.example.cryptocurency.ui.home_fragment

import com.example.cryptocurency.data.models.Coin
import java.lang.Exception

interface HomeFragmentContract {

    interface View {
        fun getCoinSuccessfully(coins: List<Coin>)
        fun getCoinFailed(exception: Exception)
        fun displayLoading()
        fun hideLoading()
    }

    interface Presenter {
        fun getCoin(sort: String, limit: Int)
    }
}
