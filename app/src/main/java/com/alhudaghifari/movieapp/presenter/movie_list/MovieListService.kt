package com.alhudaghifari.movieapp.presenter.movie_list

import android.content.Context
import com.alhudaghifari.movieapp.model.MovieListModel
import com.alhudaghifari.movieapp.presenter.ApiConstant
import com.alhudaghifari.movieapp.presenter.ApiService
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieListService {

    @GET("3/movie/{category}")
    fun getMovie(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") itemsPerPage: String = ApiConstant().apiKey,
    ): Observable<Response<MovieListModel>>

    companion object {
        fun create(): MovieListService {
            val api = ApiService.create()
            return api.create(MovieListService::class.java)
        }
    }
}