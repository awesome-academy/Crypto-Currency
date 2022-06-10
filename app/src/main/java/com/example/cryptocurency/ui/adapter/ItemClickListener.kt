package com.example.cryptocurency.ui.adapter

interface ItemClickListener<T> {
    fun onItemClick(item: T?)
    fun onItemLongClick(item: T?): Boolean
}
