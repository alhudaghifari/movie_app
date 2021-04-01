package com.alhudaghifari.movieapp.presenter.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alhudaghifari.movieapp.db.DatabaseHelper
import com.alhudaghifari.movieapp.db.entity.Movie
import com.alhudaghifari.movieapp.utils.Resource
import kotlinx.coroutines.launch

class FavoriteDbPresenter(private val dbHelper: DatabaseHelper) :
    ViewModel() {
    private val movies = MutableLiveData<Resource<List<Movie>>>()
    private val singleMovie = MutableLiveData<Resource<List<Movie>>>()

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

    fun fetchMovieById(idMovie: String) {
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

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return movies
    }

    fun getSingleMovie(): LiveData<Resource<List<Movie>>> {
        return singleMovie
    }
}