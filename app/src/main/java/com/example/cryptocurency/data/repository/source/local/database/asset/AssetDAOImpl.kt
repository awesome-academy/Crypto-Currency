package com.example.cryptocurency.data.repository.source.local.database.asset

import android.content.ContentValues
import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.AssetEntry
import com.example.cryptocurency.data.repository.source.local.database.DatabaseHelper
import com.example.cryptocurency.data.repository.source.local.database.DatabaseHelper.Companion.TABLE_ASSET

class AssetDAOImpl(DBHelper: DatabaseHelper) : IAssetDAO {

    private val writeDB = DBHelper.writableDatabase
    private val readDB = DBHelper.readableDatabase

    override fun getAllAsset(): List<Asset> {
        val cursor = readDB.query(
            DatabaseHelper.TABLE_ASSET,
            null,
            null,
            null,
            null,
            null,
            null
        )
        val result = mutableListOf<Asset>()
        if (cursor.moveToFirst()) {
            do {
                Asset().apply {
                    id = cursor.getInt(0)
                    coinId = cursor.getString(1)
                    coinName = cursor.getString(2)
                    coinSymbol = cursor.getString(3)
                    iconUrl = cursor.getString(4)
                    count = cursor.getString(5)
                    purchasePrice = cursor.getString(6)
                    purchaseTime = cursor.getLong(7)
                    result.add(this)
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        return result
    }

    override fun insertAsset(asset: Asset) {
        writeDB.insert(TABLE_ASSET, null, createContentValue(asset))
    }

    override fun updateAsset(asset: Asset) {
        val selection = "${AssetEntry.ID} = ?"
        val selectionArgs = arrayOf(asset.id.toString())
        writeDB.update(
            TABLE_ASSET,
            createContentValue(asset),
            selection,
            selectionArgs
        )
    }

    override fun deleteAsset(assetId: Int) {
        writeDB.delete(
            TABLE_ASSET,
            "${AssetEntry.ID} = ?",
            arrayOf(assetId.toString())
        )
    }

    private fun createContentValue(asset: Asset) = ContentValues().apply {
        put(AssetEntry.COIN_ID, asset.coinId)
        put(AssetEntry.COIN_NAME, asset.coinName)
        put(AssetEntry.COIN_SYMBOL, asset.coinSymbol)
        put(AssetEntry.ICON_URL, asset.iconUrl)
        put(AssetEntry.COUNT, asset.count)
        put(AssetEntry.PURCHASE_PRICE, asset.purchasePrice)
        put(AssetEntry.PURCHASE_TIME, asset.purchaseTime)
    }

    companion object {
        private var instance: AssetDAOImpl? = null

        fun getInstance(db: DatabaseHelper) = synchronized(this) {
            instance ?: AssetDAOImpl(db).also { instance = it }
        }
    }
}
