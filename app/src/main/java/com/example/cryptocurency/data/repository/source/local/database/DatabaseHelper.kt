package com.example.cryptocurency.data.repository.source.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cryptocurency.data.models.AssetEntry
import com.example.cryptocurency.data.models.CoinEntry

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_COINS)
        db?.execSQL(CREATE_TABLE_ASSET)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(dropTable(TABLE_COINS))
        db?.execSQL(dropTable(TABLE_ASSET))
        onCreate(db)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "coins_db"
        const val TABLE_COINS = "coins_tb"
        const val TABLE_ASSET = "assets_tb"

        private val CREATE_TABLE_COINS = String.format(
            "CREATE TABLE %s (%s TEXT PRIMARY KEY,  %s TEXT, %s TEXT, %s TEXT, %s TEXT," +
                    " %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT,%s INTEGER)",
            TABLE_COINS,
            CoinEntry.ID,
            CoinEntry.NAME,
            CoinEntry.SYMBOL,
            CoinEntry.PRICE,
            CoinEntry.CHANGE,
            CoinEntry.MARKET_CAP,
            CoinEntry.ICON_URL,
            CoinEntry.N_24H_VOLUME_LOCAL,
            CoinEntry.BTC_PRICE,
            CoinEntry.COLOR,
            CoinEntry.RANK
        )

        private val CREATE_TABLE_ASSET = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT, %s TEXT, %s TEXT,%s INTEGER)",
            TABLE_ASSET,
            AssetEntry.ID,
            AssetEntry.COIN_ID,
            AssetEntry.COUNT,
            AssetEntry.PURCHASE_PRICE,
            AssetEntry.PURCHASE_TIME
        )

        private fun dropTable(name: String) = String.format("DROP TABLE IF EXISTS %s", name)

        private var instance: DatabaseHelper? = null

        fun getInstance(context: Context?) = synchronized(this) {
            instance ?: DatabaseHelper(context).also { instance = it }
        }
    }
}
