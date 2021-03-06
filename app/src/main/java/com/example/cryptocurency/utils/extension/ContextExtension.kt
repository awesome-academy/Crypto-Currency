package com.example.cryptocurency.utils.extension

import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

fun Context.showToast(message: CharSequence?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.showConfirmDialog(title: CharSequence?, message: CharSequence?, positiveAction: () -> Unit){
    val negativeText ="No"
    val positiveText = "Yes"
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setCancelable(false)
        setPositiveButton(positiveText) { _, _ ->
            positiveAction()
        }
        setNegativeButton(negativeText) { dialog, _ ->
            dialog.dismiss()
        }
        create()
        show()
    }
}

fun Context.showNotificationDialog(title: String, message: String, btnName: String){
    AlertDialog.Builder(this).apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(btnName) { dialog, _ ->
            dialog.dismiss()
        }
        show()
    }
}
