package com.example.cryptocurency.utils.extension

import android.widget.TextView
import androidx.core.content.ContextCompat

fun TextView.setTextColorFromResource(color: Int){
    setTextColor(
        ContextCompat.getColor(context, color)
    )
}
