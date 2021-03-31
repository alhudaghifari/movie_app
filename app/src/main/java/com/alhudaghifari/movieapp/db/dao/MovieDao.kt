package com.alhudaghifari.movieapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.alhudaghifari.movieapp.db.entity.Movie


@Dao
interface MovieDao {

    @Query("SELECT * FROM movie")
    suspend fun getAll(): List<Movie>

    @Query("SELECT * FROM movie where idMovie = :idMovie")
    suspend fun getDataById(idMovie: String): List<Movie>

    @Insert
    suspend fun insertAll(movies: List<Movie>)

    @Delete
    suspend fun delete(movie: Movie)

}