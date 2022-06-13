package com.example.cryptocurency.data.repository.source.remote.connection

import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.models.CoinEntry
import org.json.JSONObject

object JsonHelper {
    fun getCoinsDataFromJson(responseString: String): MutableList<Coin>? {
        val jsonObject = JSONObject(responseString)
        val listResult = mutableListOf<Coin>()
        return try {
            val jsonCoins = jsonObject.getJSONObject(CoinEntry.DATA).getJSONArray(CoinEntry.COINS)
            for (i in 0 until jsonCoins.length()) {
                listResult.add(parseJsonToCoin(jsonCoins.optJSONObject(i), false))
            }
            listResult
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCoinDetailFromJson(responseString: String): Coin? {
        val jsonObject = JSONObject(responseString)
        return try {
            val jsonCoin = jsonObject.getJSONObject(CoinEntry.DATA).getJSONObject(CoinEntry.COIN)
            parseJsonToCoin(jsonCoin, true)
        } catch (e: Exception) {
            null
        }
    }

    private fun parseJsonToCoin(json: JSONObject, detail: Boolean) = Coin().apply {
        json.let {
            id = it.optString(CoinEntry.ID)
            name = it.optString(CoinEntry.NAME)
            symbol = it.optString(CoinEntry.SYMBOL)
            iconUrl = it.optString(CoinEntry.ICON_URL)
            marketCap = it.optString(CoinEntry.MARKET_CAP)
            price = it.optString(CoinEntry.PRICE)
            rank = it.optInt(CoinEntry.RANK)
            btcPrice = it.optString(CoinEntry.BTC_PRICE)
            color = it.optString(CoinEntry.COLOR)
            chance = it.optString(CoinEntry.CHANCE)
            n24hVolume = it.optString(CoinEntry.N_24H_VOLUME)
            if (detail) {
                coinDetail.apply {
                    priceAt = it.optLong(CoinEntry.PRICE_AT)
                    numberOfMarkets = it.optLong(CoinEntry.NUMBER_OF_MARKETS)
                    numberOfExchanges = it.optLong(CoinEntry.NUMBER_OF_EXCHANGES)
                    websiteUrl = it.optString(CoinEntry.WEBSITE_URL)
                    description = it.optString(CoinEntry.DESCRIPTION)
                    val jsonSpark = it.optJSONArray(CoinEntry.SPARKLINE)
                    val list = ArrayList<String>()
                    jsonSpark?.let { arr ->
                        for (i in 0 until arr.length()) {
                            list.add(arr.optString(i))
                        }
                    }
                    sparkline = list
                }
            }
        }
    }
}
