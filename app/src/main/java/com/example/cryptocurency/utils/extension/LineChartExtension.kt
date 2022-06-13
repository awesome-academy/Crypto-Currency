package com.example.cryptocurency.utils.extension

import android.graphics.Color
import com.example.cryptocurency.utils.LABEL_PRICE
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

fun LineChart.createCoinChart(sparkline: ArrayList<String>, coinColor: String){
    val lineList = ArrayList<Entry>()
    for (i in 0 until sparkline.size){
        lineList.add(Entry(i.toFloat(), sparkline[i].toFloat()))
    }
    val lineDataSet= LineDataSet(lineList, LABEL_PRICE)
    val lineData = LineData(lineDataSet)
    lineDataSet.apply {
        setDrawFilled(true)
        try{
            val mColor = Color.parseColor(coinColor)
            color = mColor
            fillColor = mColor
        } catch (e: RuntimeException){
            e.printStackTrace()
        }
    }
    this.data = lineData
    isSelected = true
}
