package com.example.cryptocurency.ui.search

import com.example.cryptocurency.data.models.Coin

interface SearchActivityContract {

    interface View{
        fun getCoinsDataSuccessfully(coins: MutableList<Coin>)
        fun getCoinsDataFailure(exception: Exception?)
        fun searchCoinsSuccessfully(coins: MutableList<Coin>)
        fun searchCoinsFailure(exception: Exception?)
    }

    interface Presenter{
        fun getCoinsData()
        fun searchCoins(query: String)
    }
}
