package com.alhudaghifari.movieapp.presenter.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhudaghifari.movieapp.db.DatabaseHelper
import com.alhudaghifari.movieapp.db.entity.Movie
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.utils.Resource
import kotlinx.coroutines.launch

class FavoriteDbHelper(private val dbHelper: DatabaseHelper) :
    ViewModel() {
    private val movies = MutableLiveData<Resource<List<Movie>>>()
    private val singleMovie = MutableLiveData<Resource<List<Movie>>>()
    private val changeStatusFavorite = MutableLiveData<Resource<Boolean>>()

    fun fetchMovies() {
        viewModelScope.launch {
            movies.postValue(Resource.loading(null))
            try {
                val moviesFromDb = dbHelper.getFavoriteMovie()
                if (moviesFromDb.isNotEmpty()) {
                    movies.postValue(Resource.success(moviesFromDb))
                } else {
                    movies.postValue(Resource.success(listOf()))
                }
            } catch (e: Exception) {
                movies.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun fetchMovieById(idMovie: Int) {
        viewModelScope.launch {
            singleMovie.postValue(Resource.loading(null))
            try {
                val moviesFromDb = dbHelper.getMovieById(idMovie)
                if (moviesFromDb.isNotEmpty()) {
                    singleMovie.postValue(Resource.success(moviesFromDb))
                } else {
                    singleMovie.postValue(Resource.success(listOf()))
                }
            } catch (e: Exception) {
                singleMovie.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun addFavorite(itemMovie: ItemMovie) {
        viewModelScope.launch {
            changeStatusFavorite.postValue(Resource.loading(null))
            try {
                val movie = Movie(
                    itemMovie.id!!,
                    itemMovie.title,
                    itemMovie.releaseDate,
                    itemMovie.posterPath,
                )
                val movieToInsertInDb = mutableListOf<Movie>()
                movieToInsertInDb.add(movie)

                dbHelper.insertAll(movieToInsertInDb)
                changeStatusFavorite.postValue(Resource.success(true))
            } catch (e: Exception) {
                changeStatusFavorite.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun deleteFavorite(itemMovie: ItemMovie) {
        viewModelScope.launch {
            changeStatusFavorite.postValue(Resource.loading(null))
            try {
                val movie = Movie(
                    itemMovie.id!!,
                    itemMovie.title,
                    itemMovie.releaseDate,
                    itemMovie.posterPath,
                )

                dbHelper.deleteData(movie)
                changeStatusFavorite.postValue(Resource.success(false))
            } catch (e: Exception) {
                changeStatusFavorite.postValue(Resource.error("Something went wrong", null))
            }
        }
    }

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return movies
    }

    fun getSingleMovie(): LiveData<Resource<List<Movie>>> {
        return singleMovie
    }

    fun getStatusFavorite() : LiveData<Resource<Boolean>> {
        return changeStatusFavorite
    }
}