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

        }
    }

    fun getMovies(): LiveData<Resource<List<Movie>>> {
        return movies
    }
}