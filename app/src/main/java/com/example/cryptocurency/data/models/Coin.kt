package com.example.cryptocurency.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel

@Parcelize
data class Coin(
    var id: String = "",
    var name: String = "",
    var symbol: String = "",
    var color: String = "",
    var iconUrl: String = "",
    var marketCap: String = "0",
    var price: String = "0",
    var btcPrice: String = "0",
    var chance: String = "0",
    var rank: Int= 0,
    var n24hVolume: String = "0",
    var coinDetail : CoinDetail = CoinDetail()
): Parcelable

@Parcelize
data class CoinDetail(
    var websiteUrl: String = "",
    var priceAt: Long = 0,
    var numberOfMarkets: Long = 0,
    var numberOfExchanges: Long = 0,
    var sparkline: ArrayList<String> = arrayListOf(),
    var description: String ="",
):Parcelable

object CoinEntry{
    const val DATA = "data"
    const val COINS = "coins"
    const val COIN = "coin"
    const val ID = "uuid"
    const val NAME = "name"
    const val SYMBOL = "symbol"
    const val RANK = "rank"
    const val ICON_URL = "iconUrl"
    const val MARKET_CAP = "marketCap"
    const val COLOR = "color"
    const val PRICE = "price"
    const val PRICE_AT = "priceAt"
    const val BTC_PRICE = "btcPrice"
    const val CHANCE = "change"
    const val N_24H_VOLUME = "24hVolume"
    const val N_24H_VOLUME_LOCAL = "n24hVolume"
    const val SPARKLINE = "sparkline"
    const val NUMBER_OF_EXCHANGES = "numberOfExchanges"
    const val NUMBER_OF_MARKETS = "numberOfMarkets"
    const val WEBSITE_URL = "websiteUrl"
    const val DESCRIPTION = "description"
}
