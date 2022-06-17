package com.example.cryptocurency.utils.extension

import android.graphics.Color.green
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.cryptocurency.R

fun TextView.setTextColorFromResource(color: Int) {
    setTextColor(
        ContextCompat.getColor(context, color)
    )
}

fun TextView.setTextColorWithPrice(price: Double) {
    if (price >= 0) {
        this.setTextColorFromResource(R.color.color_green)
    } else {
        this.setTextColorFromResource(R.color.color_red)
    }
}

fun TextView.setTextWithSelected(value: String) {
    text = value
    isSelected = true
}
