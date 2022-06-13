package com.example.cryptocurency.ui.detail

import com.example.cryptocurency.data.models.Coin

interface DetailActivityContract {

    interface View{
        fun getCoinDetailSuccessfully(coin: Coin)
        fun getCoinDetailFailure(exception: Exception?)
        fun checkFavoriteSuccessfully(isFavorite :Boolean)
        fun checkFavoriteFailure(exception: Exception?)
        fun updateFavoriteSuccessfully(isFavorite :Boolean)
        fun updateFavoriteFailure(exception: Exception?)
    }

    interface Presenter{
        fun getCoinDetail(coin: Coin)
        fun checkFavorite(coin: Coin)
        fun handleFavoriteClick(coin: Coin, isFavorite: Boolean)
    }
}
