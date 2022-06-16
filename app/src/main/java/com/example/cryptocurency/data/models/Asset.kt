package com.example.cryptocurency.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Asset(
    var id: Int = 0,
    var coinId: String = "",
    var coinName: String = "",
    var coinSymbol: String ="",
    var iconUrl: String ="",
    var count: String = "0",
    var purchasePrice: String = "0",
    var purchaseTime: Long = 0,
    var currentPrice: String? = null
): Parcelable

object AssetEntry{
    const val ID = "id"
    const val COIN_ID = "coinId"
    const val COIN_NAME = "coinName"
    const val COIN_SYMBOL = "coinSymbol"
    const val ICON_URL = "iconUrl"
    const val COUNT = "count"
    const val PURCHASE_PRICE = "purchasePrice"
    const val PURCHASE_TIME = "purchaseTime"
}
