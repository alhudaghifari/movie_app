package com.alhudaghifari.movieapp.presenter.review

import com.alhudaghifari.movieapp.model.ErrorModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception

class ReviewPresenter(private val view: ReviewInterface) {
    private var disposable: Disposable? = null

    private val apiService by lazy {
        ReviewService.create()
    }

    fun disposeThis() {
        disposable?.dispose()
    }

    fun getReview(page: Int, idMoview: String) {
        view.showLoading()
        disposable = apiService.getReview(idMoview, page)
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