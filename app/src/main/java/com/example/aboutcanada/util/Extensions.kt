package com.example.aboutcanada.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.t(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.t(@StringRes message: Int) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}