package com.example.todoapplication.model.api

typealias BackendCallback<T> = (BackendResult<T>) -> Unit

sealed class BackendResult<T> {
    class Success<T>(val data: T) : BackendResult<T>()
    class Error<T>(val message: String) : BackendResult<T>()
}