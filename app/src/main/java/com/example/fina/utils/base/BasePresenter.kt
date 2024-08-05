package com.example.fina.utils.base

interface BasePresenter<T> {
    fun onStart()

    fun onStop()

    fun setView(view: T?)
}
