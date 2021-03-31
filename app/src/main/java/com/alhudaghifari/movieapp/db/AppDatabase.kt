package com.alhudaghifari.movieapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alhudaghifari.movieapp.db.dao.MovieDao
import com.alhudaghifari.movieapp.db.entity.Movie


@Database(entities = [Movie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}