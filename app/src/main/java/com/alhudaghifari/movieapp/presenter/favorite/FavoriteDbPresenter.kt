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

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
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

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return movies
    }
}