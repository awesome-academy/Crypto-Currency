package com.example.cryptocurency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Asset
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ItemAssetLayoutBinding
import com.example.cryptocurency.utils.PERCENT_NUM
import com.example.cryptocurency.utils.extension.*

class MyAssetAdapter(private val mListener: ItemClickListener<Asset>) :
    RecyclerView.Adapter<MyAssetAdapter.ViewHolder>() {

    private var mList = mutableListOf<Asset>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAssetLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            mList[position].apply {
                assetItem = this
                binding.apply {
                    imageCoin.loadImageSVG(iconUrl, R.drawable.img_default_coin)
                    txtAssetName.setTextWithSelected(coinName)
                    textAssetSymbol.setTextWithSelected(coinSymbol)
                    txtPurchasePrice.setTextWithSelected(purchasePrice.getCoinPrice())
                    txtAssetValue.setTextWithSelected(count)
                    currentPrice?.let {
                        txtCurrentPrice.setTextWithSelected(it.getCoinPrice())
                        it.toDoubleOrNull()?.let { to ->
                            purchasePrice.toDoubleOrNull()?.let { from ->
                                val change = ((to - from) * PERCENT_NUM / from).roundInt()
                                txtPriceChange.setTextWithSelected(
                                    change.toString().getCoinChange()
                                )
                                txtPriceChange.setTextColorWithPrice(change)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = mList.size

    fun getAssets() = mList

    fun setAdapterData(list: MutableList<Asset>) {
        mList.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    fun updateAdapterData(list: MutableList<Coin>) {
        if (list.isEmpty()) {
            return
        }
        for (i in 0 until mList.size) {
            list.firstOrNull { it.id == mList[i].coinId }?.apply {
                price.toDoubleOrNull()?.let { pr ->
                    mList[i].apply {
                        count.toDoubleOrNull()?.let { co ->
                            currentPrice = (pr * co).roundInt().toString()
                            notifyItemChanged(i)
                        }
                    }
                }
            }
        }
    }

    fun deleteAdapterData(asset: Asset) {
        mList.indexOfFirst { it.id == asset.id }.apply {
            if (this != -1) {
                mList.removeAt(this)
                notifyItemRemoved(this)
            }
        }
    }

    inner class ViewHolder(
        val binding: ItemAssetLayoutBinding,
        listener: ItemClickListener<Asset>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var assetItem: Asset? = null

        init {
            itemView.setOnClickListener {
                listener.onItemClick(assetItem)
            }
            itemView.setOnLongClickListener {
                listener.onItemLongClick(assetItem)
            }
        }
    }
}
