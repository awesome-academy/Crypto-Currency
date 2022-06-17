package com.example.cryptocurency.utils.extension

import com.example.cryptocurency.utils.ROUND_DEFAULT
import kotlin.math.round

fun Double.roundInt(decimals: Int = ROUND_DEFAULT): Double {
    val unit = 10
    var multiplier = 1.0
    repeat(decimals) { multiplier *= unit }
    return round(this * multiplier) / multiplier
}
