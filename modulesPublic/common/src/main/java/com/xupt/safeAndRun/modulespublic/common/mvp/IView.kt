package com.example.common.mvp

import androidx.lifecycle.LifecycleOwner

interface IView : LifecycleOwner {
    fun showLoading(msg: String)

    fun dismissLoading()

    fun showToast(msg: String)

    fun showLoadingLayout()

    fun showSuccessLayout()

    fun showErrorLayout()

    fun showEmptyLayout()
}