package com.example.fina.data.repository

interface OnResultListener<T> {
    fun onSuccess(data: T)

    fun onFailure(exception: Exception?)
}
