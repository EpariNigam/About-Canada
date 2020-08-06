package com.example.aboutcanada.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.t(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.t(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.isConnectedToInternet(): Boolean {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
        val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return manager.activeNetworkInfo?.isConnectedOrConnecting ?: false
    } else {
        return false
    }
}