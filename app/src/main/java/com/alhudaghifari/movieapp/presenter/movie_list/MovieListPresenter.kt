package com.alhudaghifari.movieapp.presenter.movie_list

import android.util.Log
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.model.ErrorModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class MovieListPresenter(private val view: MovieListInterface) {
    private var disposable: Disposable? = null


    private val apiService by lazy {
        MovieListService.create()
    }

    fun disposeThis() {
        disposable?.dispose()
    }

    fun getPopular(page: Int) {
        getMovie(page, "popular")
    }

    fun getUpcoming(page: Int){
        getMovie(page, "upcoming")
    }

    fun getTopRated(page: Int){
        getMovie(page, "top_rated")
    }

    fun getNowPlaying(page: Int){
        getMovie(page, "now_playing")
    }

    fun getMovie(page: Int, category: String) {
        view.showLoading()
        disposable = apiService.getMovie(category, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result ->
                    result.code()
                    view.hideLoading()
                    if (result.code() == 200) {
                        view.callFinished(result.body()!!)
                    }  else if (result.code() == 500) {
                        view.showError("500")
                    } else {
                        val gson = Gson()
                        var errorModel: ErrorModel? = ErrorModel()
                        try {
                             errorModel =gson.fromJson(result.errorBody()!!.string(), ErrorModel::class.java)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        view.showError(errorModel?.statusMessage ?: "")
                    }
                },
                { error ->
                    view.showError(error.message)
                    view.hideLoading()
                }
            )
    }
}