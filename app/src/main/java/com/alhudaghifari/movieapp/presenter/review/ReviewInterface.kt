package com.alhudaghifari.movieapp.presenter.review

import com.alhudaghifari.movieapp.model.MovieListModel
import com.alhudaghifari.movieapp.model.ReviewModel
import com.alhudaghifari.movieapp.presenter.ConnStatus

interface ReviewInterface: ConnStatus {
    fun callFinished(model: ReviewModel)
}