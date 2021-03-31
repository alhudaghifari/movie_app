package com.alhudaghifari.movieapp.presenter.movie_list

import com.alhudaghifari.movieapp.model.MovieListModel
import com.alhudaghifari.movieapp.presenter.ConnStatus

interface MovieListInterface: ConnStatus {
    fun callFinished(model: MovieListModel)
}