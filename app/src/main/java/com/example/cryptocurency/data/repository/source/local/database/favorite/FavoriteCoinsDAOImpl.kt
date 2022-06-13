package com.example.cryptocurency.data.repository.source.local.database.favorite

import android.content.ContentValues
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.models.CoinEntry
import com.example.cryptocurency.data.repository.source.local.database.DatabaseHelper

class FavoriteCoinsDAOImpl(DBHelper: DatabaseHelper) : IFavoriteCoinsDAO {

    private val writeDB = DBHelper.writableDatabase
    private val readDB = DBHelper.readableDatabase

    override fun getAllFavoriteCoins(): List<Coin> {
        val cursor = readDB.query(
            DatabaseHelper.TABLE_COINS,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val result = mutableListOf<Coin>()
        if (cursor.moveToFirst()) {
            do {
                Coin().apply {
                    id = cursor.getString(0)
                    name = cursor.getString(1)
                    symbol = cursor.getString(2)
                    price = cursor.getString(3)
                    chance = cursor.getString(4)
                    marketCap = cursor.getString(5)
                    iconUrl = cursor.getString(6)
                    n24hVolume = cursor.getString(7)
                    btcPrice = cursor.getString(8)
                    color = cursor.getString(9)
                    rank = cursor.getInt(10)
                    result.add(this)
                }
            } while (cursor.moveToNext())
        }
        return result
    }

    override fun insertCoin(coin: Coin) {
        writeDB.insert(DatabaseHelper.TABLE_COINS, null, createContentValue(coin))
    }

    override fun updateCoin(coin: Coin) {
        val selection = "${CoinEntry.ID} = ?"
        val selectionArgs = arrayOf(coin.id)
        writeDB.update(
            DatabaseHelper.TABLE_COINS,
            createContentValue(coin),
            selection,
            selectionArgs)
    }

    override fun deleteCoin(coinId: String) {
        writeDB.delete(DatabaseHelper.TABLE_COINS, "${CoinEntry.ID} = ?", arrayOf(coinId))
    }

    override fun isFavorite(coinId: String): Boolean {
        val selection = "${CoinEntry.ID} = ?"
        val selectionArgs = arrayOf(coinId)
        val cursor = readDB.query(
            DatabaseHelper.TABLE_COINS,
            null,
            selection,
            selectionArgs,
            null,
            null,
            null
        )
        return cursor.count > 0
    }

    private fun createContentValue(coin: Coin) = ContentValues().apply {
        put(CoinEntry.ID, coin.id)
        put(CoinEntry.NAME, coin.name)
        put(CoinEntry.SYMBOL, coin.symbol)
        put(CoinEntry.PRICE, coin.price)
        put(CoinEntry.CHANCE, coin.chance)
        put(CoinEntry.MARKET_CAP, coin.marketCap)
        put(CoinEntry.ICON_URL, coin.iconUrl)
        put(CoinEntry.N_24H_VOLUME_LOCAL, coin.n24hVolume)
        put(CoinEntry.BTC_PRICE, coin.btcPrice)
        put(CoinEntry.COLOR, coin.color)
        put(CoinEntry.RANK, coin.rank)
    }

    companion object {
        private var instance: FavoriteCoinsDAOImpl? = null

        fun getInstance(db: DatabaseHelper) = synchronized(this) {
            instance ?: FavoriteCoinsDAOImpl(db).also { instance = it }
        }
    }
}
