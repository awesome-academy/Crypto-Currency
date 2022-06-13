package com.example.cryptocurency.utils.extension

fun String.getCoinPrice() = "$${this}"

fun String.getCoinChange() = toDoubleOrNull()?.let {
    if (it < 0) "${this}%"
    else "+${this}%"
} ?: this

fun String.getCoinRank() = "#${this}"
