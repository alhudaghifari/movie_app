package com.alhudaghifari.movieapp.utils

import android.content.Context
import android.widget.Toast


/**
 * to show toast
 */
fun showToast(context: Context, msg: String) {
    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
}