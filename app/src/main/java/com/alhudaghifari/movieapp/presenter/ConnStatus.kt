package com.alhudaghifari.movieapp.presenter

interface ConnStatus {
    fun showLoading()
    fun hideLoading()
    fun showError(msg: String?)
}