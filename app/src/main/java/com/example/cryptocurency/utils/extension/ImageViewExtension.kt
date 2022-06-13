package com.example.cryptocurency.utils.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.bumptech.glide.Glide

fun ImageView.loadImageCircleWithUrl(url: String, placeholder: Int) {
    Glide.with(this)
        .load(url)
        .placeholder(placeholder)
        .circleCrop()
        .into(this)
}

fun ImageView.loadImageSVG(url: String, placeholder: Int) {
    val imageLoader = ImageLoader.Builder(context)
        .componentRegistry { add(SvgDecoder(context)) }
        .build()
    val request = ImageRequest.Builder(this.context)
        .placeholder(placeholder)
        .data(url)
        .target(this)
        .build()
    imageLoader.enqueue(request)
}
