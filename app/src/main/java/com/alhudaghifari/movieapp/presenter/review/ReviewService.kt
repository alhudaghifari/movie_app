package com.alhudaghifari.movieapp.presenter.review

import com.alhudaghifari.movieapp.model.ReviewModel
import com.alhudaghifari.movieapp.presenter.ApiConstant
import com.alhudaghifari.movieapp.presenter.ApiService
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ReviewService {
    @GET("3/movie/{idMovie}/reviews")
    fun getReview(
        @Path("idMovie") idMovie: String,
        @Query("page") page: Int,
        @Query("api_key") itemsPerPage: String = ApiConstant().apiKey,
    ): Observable<Response<ReviewModel>>

    companion object {
        fun create(): ReviewService {
            val api = ApiService.create()
            return api.create(ReviewService::class.java)
        }
    }
}