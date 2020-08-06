package com.example.aboutcanada.viewmodel

sealed class AppResult<out T : Any> {
    data class Success<out T : Any>(val output: T) : AppResult<T>()
    data class Error(val error: String) : AppResult<String>()
    data class IntError(val error: Int) : AppResult<Int>()
    object Loading : AppResult<Nothing>()
}