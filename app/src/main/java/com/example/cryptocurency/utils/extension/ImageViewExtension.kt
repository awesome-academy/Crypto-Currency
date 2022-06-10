package com.example.cryptocurency.utils.extension

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadImageCircleWithUrl(url: String, placeholder: Int) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageWithUrl(url: String, placeholder: Int) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .into(this)
}
