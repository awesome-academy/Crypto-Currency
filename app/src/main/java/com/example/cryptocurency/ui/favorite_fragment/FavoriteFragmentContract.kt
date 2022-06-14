package com.example.cryptocurency.ui.favorite_fragment

import com.example.cryptocurency.data.models.Coin

interface FavoriteFragmentContract {

    interface View{
        fun getFavoriteSuccessfully(list: MutableList<Coin>)
        fun getFavoriteFailure(exception: Exception?)
        fun updateFavoriteSuccessfully(list: MutableList<Coin>)
        fun updateFavoriteFailure(exception: Exception?)
        fun removeFavoriteCoinSuccessfully(coin: Coin)
        fun removeFavoriteCoinFailure(exception: Exception?)
    }

    interface Presenter{
        fun getFavoriteList()
        fun updateFavoriteListData(list: MutableList<Coin>)
        fun removeFavoriteCoin(coin: Coin)
    }
}
