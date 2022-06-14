package com.example.cryptocurency.ui.exchange

import com.example.cryptocurency.data.models.Coin

interface ExchangeActivityContract {

    interface View{
        fun getCoinsDataSuccessfully(coins: MutableList<Coin>)
        fun getCoinsDataFailure(exception :Exception?)
    }

    interface Presenter{
        fun getCoinsData()
    }
}
