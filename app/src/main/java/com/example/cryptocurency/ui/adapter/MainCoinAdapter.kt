package com.example.cryptocurency.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ItemCoinMainLayoutBinding
import com.example.cryptocurency.utils.extension.getCoinChange
import com.example.cryptocurency.utils.extension.getCoinPrice
import com.example.cryptocurency.utils.extension.loadImageWithUrl
import com.example.cryptocurency.utils.extension.setTextColorFromResource

class MainCoinAdapter(private val mListener: ItemClickListener<Coin>) :
    RecyclerView.Adapter<MainCoinAdapter.ViewHolder>() {

    private var mList = mutableListOf<Coin>()
    private var mContext: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val binding = ItemCoinMainLayoutBinding.inflate(LayoutInflater.from(mContext))
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(mList[position]) {
                coinItem = this
                with(binding) {
                    txtName.text = name
                    txtSymbol.text = symbol
                    txtChange.text = chance.getCoinChange()
                    txtPrice.text = price.getCoinPrice()
                    icCoin.loadImageWithUrl(iconUrl, R.drawable.img_default_coin)
                    chance.toDoubleOrNull()?.let {
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
        mList = list
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
