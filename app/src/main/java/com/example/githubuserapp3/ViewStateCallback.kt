package com.example.githubuserapp3

import android.view.View

interface ViewStateCallBack<T> {

    fun onLoading()
    fun onSuccess(data: T)
    fun onFailed(message: String?)

    val visible : Int
        get() = View.VISIBLE

    val invisible: Int
        get() = View.INVISIBLE
}