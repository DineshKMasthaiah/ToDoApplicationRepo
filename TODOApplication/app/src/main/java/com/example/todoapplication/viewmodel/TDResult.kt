package com.example.todoapplication.viewmodel

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class TDResult<out T : Any> {

    data class Success<out T : Any>(val data: T) : TDResult<T>()
    data class Error(val exception: Exception) : TDResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
        }
    }
}