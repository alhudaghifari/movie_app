package com.alhudaghifari.movieapp.db

import com.alhudaghifari.movieapp.db.entity.Movie


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getFavoriteMovie(): List<Movie> = appDatabase.movieDao().getAll()

    override suspend fun insertAll(movie: List<Movie>) = appDatabase.movieDao().insertAll(movie)

}