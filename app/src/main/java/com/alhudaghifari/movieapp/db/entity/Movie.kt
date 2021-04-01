package com.alhudaghifari.movieapp.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Movie(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "releasedDate") val releasedDate: String?,
    @ColumnInfo(name = "posterPath") val posterPath: String?
)