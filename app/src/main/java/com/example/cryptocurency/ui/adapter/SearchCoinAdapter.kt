package com.example.cryptocurency.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptocurency.R
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.databinding.ItemCoinSearchLayoutBinding
import com.example.cryptocurency.utils.extension.loadImageSVG

class SearchCoinAdapter(private val mListener: ItemClickListener<Coin>):
    RecyclerView.Adapter<SearchCoinAdapter.ViewHolder>()  {

    private var mContext: Context? = null
    private var mList = mutableListOf<Coin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchCoinAdapter.ViewHolder {
        mContext = parent.context
        val binding = ItemCoinSearchLayoutBinding.inflate(LayoutInflater.from(mContext))
        return ViewHolder(binding, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            mList[position].apply {
                coinItem = this
                binding.apply {
                    imageCoin.loadImageSVG(iconUrl, R.drawable.img_default_coin)
                    txtName.text = name
                    txtSymbol.text = symbol
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

    inner class ViewHolder(
        val binding: ItemCoinSearchLayoutBinding,
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
