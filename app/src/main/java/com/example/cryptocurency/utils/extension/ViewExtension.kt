package com.example.cryptocurency.utils.extension

import android.view.View
import androidx.core.content.ContextCompat
import com.example.cryptocurency.R
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT)
        .setBackgroundTint(
            ContextCompat.getColor(
                context, R.color.color_green
            )
        )
        .show()
}
