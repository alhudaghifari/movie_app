package com.alhudaghifari.movieapp.db

import com.alhudaghifari.movieapp.db.entity.Movie


class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getFavoriteMovie(): List<Movie> = appDatabase.movieDao().getAll()

    override suspend fun getMovieById(idMovie: Int): List<Movie> = appDatabase.movieDao().getDataById(idMovie)

    override suspend fun insertAll(movie: List<Movie>) = appDatabase.movieDao().insertAll(movie)

    override suspend fun deleteData(movie: Movie) = appDatabase.movieDao().delete(movie)

}