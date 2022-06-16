package com.example.cryptocurency.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.data.models.CoinEntry
import com.example.cryptocurency.databinding.ItemCoinMainLayoutBinding
import com.example.cryptocurency.utils.extension.*

class MainCoinAdapter(private val mListener: ItemClickListener<Coin>) :
    RecyclerView.Adapter<MainCoinAdapter.ViewHolder>() {

    private var mList = mutableListOf<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCoinMainLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(mList[position]) {
                coinItem = this
                with(binding) {
                    txtRank.text = rank.toString().getCoinRank()
                    txtName.text = name
                    txtSymbol.text = symbol
                    txtChange.text = change.getCoinChange()
                    txtPrice.setTextWithSelected(price.getCoinPrice())
                    icCoin.loadImageSVG(iconUrl, R.drawable.img_default_coin)
                    change.toDoubleOrNull()?.let {
                        if (it < 0) {
                            txtChange.setTextColorFromResource(R.color.color_red)
                        } else {
                            txtChange.setTextColorFromResource(R.color.color_green)
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = mList.size

    fun setAdapterData(list: MutableList<Coin>) {
        mList.apply {
            clear()
            addAll(list)
            notifyDataSetChanged()
        }
    }

    fun sortAdapterData(sort: String) {
        when (sort) {
            CoinEntry.MARKET_CAP -> {
                mList.sortByDescending {
                    it.marketCap.toLong()
                }
            }
            CoinEntry.CHANGE -> {
                mList.sortByDescending {
                    it.change.toDouble()
                }
            }
            CoinEntry.N_24H_VOLUME -> {
                mList.sortByDescending {
                    it.n24hVolume.toLong()
                }
            }
            CoinEntry.PRICE -> {
                mList.sortByDescending {
                    it.price.toDouble()
                }
            }
        }
        notifyDataSetChanged()
    }

    fun updateAdapterData(list: MutableList<Coin>) {
        for (i in 0 until mList.size) {
            list.firstOrNull { it.id == mList[i].id }?.apply {
                mList[i] = this
                notifyItemChanged(i)
            }
        }
    }

    fun deleteAdapterData(coin: Coin) {
        mList.indexOfFirst { it.id == coin.id }.apply {
            if (this != -1) {
                mList.removeAt(this)
                notifyItemRemoved(this)
            }
        }
    }

    inner class ViewHolder(
        val binding: ItemCoinMainLayoutBinding,
        listener: ItemClickListener<Coin>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        var coinItem: Coin? = null

        init {
            itemView.setOnClickListener {
                listener.onItemClick(coinItem)
            }
            itemView.setOnLongClickListener {
                listener.onItemLongClick(coinItem)
            }
        }
    }
}
