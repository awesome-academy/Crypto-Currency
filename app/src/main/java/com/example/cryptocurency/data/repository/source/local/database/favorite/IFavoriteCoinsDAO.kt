package com.example.cryptocurency.data.repository.source.local.database.favorite

import com.example.cryptocurency.data.models.Coin

interface IFavoriteCoinsDAO {
    fun getAllFavoriteCoins() : List<Coin>
    fun insertCoin(coin: Coin)
    fun updateCoin(coin:Coin)
    fun deleteCoin(coinId: String)
    fun isFavorite(coinId: String) : Boolean
}
