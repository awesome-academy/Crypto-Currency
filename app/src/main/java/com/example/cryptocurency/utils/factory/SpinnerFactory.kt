package com.example.cryptocurency.utils.factory

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.cryptocurency.R
import com.example.cryptocurency.utils.extension.setTextColorFromResource

object SpinnerFactory {

    fun createSpinnerAdapter(context: Context, resources: Int) = ArrayAdapter.createFromResource(
        context,
        resources,
        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item
    )

    fun createSpinnerListener(
        handleSelect: (Int) -> Unit,
        handleNoSelect: () -> Unit
    ) = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            (view as TextView).setTextColorFromResource(R.color.color_white)
            handleSelect(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            handleNoSelect()
        }
    }
}