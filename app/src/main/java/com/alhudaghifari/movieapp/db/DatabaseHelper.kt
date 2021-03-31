package com.alhudaghifari.movieapp.db

import com.alhudaghifari.movieapp.db.entity.Movie


interface DatabaseHelper {

    suspend fun getFavoriteMovie(): List<Movie>

    suspend fun insertAll(movie: List<Movie>)

}