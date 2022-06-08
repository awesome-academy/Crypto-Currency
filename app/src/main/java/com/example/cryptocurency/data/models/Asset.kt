package com.example.cryptocurency.data.models

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class Asset(
    var id: Int = 0,
    var coinId: String = "",
    var count: String = "0",
    var purchasePrice: String = "",
    var purchaseTime: Long = 0
): Parcelable

object AssetEntry{
    const val ID = "id"
    const val COIN_ID = "coinId"
    const val COUNT = "count"
    const val PURCHASE_PRICE = "purchasePrice"
    const val PURCHASE_TIME = "purchaseTime"
}
