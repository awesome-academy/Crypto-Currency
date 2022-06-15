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

    fun createSpinnerAdapterFromArray(context: Context, arrayAdapter: ArrayList<String>) = ArrayAdapter(
        context,
        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
        arrayAdapter
    )

    fun createSpinnerListener(
        handleSelect: (Int) -> Unit,
        handleNoSelect: () -> Unit,
        isWhite: Boolean = true
    ) = object :
        AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>,
            view: View,
            position: Int,
            id: Long
        ) {
            if (isWhite) (view as TextView).setTextColorFromResource(R.color.color_white)
            handleSelect(position)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            handleNoSelect()
        }
    }
}
