package com.example.cryptocurency.utils.extension

import android.graphics.Color
import com.example.cryptocurency.data.models.Coin
import com.example.cryptocurency.utils.LABEL_PRICE
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

fun LineChart.createCoinChart(sparkline: ArrayList<String>, coinColor: String){
    this.data = LineData(createLineDataSet(sparkline, coinColor))
    isSelected = true
}

fun LineChart.createCompareChart(coin1: Coin, coin2: Coin){
    isSelected = false
    ArrayList<ILineDataSet>().apply{
        add(createLineDataSet(coin1.coinDetail.sparkline, coin1.color, coin1.symbol))
        add(createLineDataSet(coin2.coinDetail.sparkline, coin2.color, coin2.symbol))
        data = LineData(this)
    }
    isSelected = true
}

fun createLineDataSet(sparkline: ArrayList<String>, coinColor: String, label: String = LABEL_PRICE): LineDataSet{
    val lineList = ArrayList<Entry>()
    for (i in 0 until sparkline.size){
        lineList.add(Entry(i.toFloat(), sparkline[i].toFloat()))
    }
    val lineDataSet= LineDataSet(lineList, label)
    lineDataSet.apply {
        setDrawFilled(true)
        setDrawValues(false)
        try{
            val mColor = Color.parseColor(coinColor)
            color = mColor
            fillColor = mColor
        } catch (e: RuntimeException){
            e.printStackTrace()
        }
    }
    return lineDataSet
}
