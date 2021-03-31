package com.alhudaghifari.movieapp.view.detailmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityDetailMovieBinding
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.model.ItemReview
import com.alhudaghifari.movieapp.model.ReviewModel
import com.alhudaghifari.movieapp.presenter.review.ReviewInterface
import com.alhudaghifari.movieapp.presenter.review.ReviewPresenter
import com.alhudaghifari.movieapp.utils.DateFormatterUtils
import com.alhudaghifari.movieapp.utils.ImageUtils
import com.alhudaghifari.movieapp.utils.showToast

class DetailMovieActivity : AppCompatActivity(), ReviewInterface {

    private lateinit var itemMovie: ItemMovie;
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var presenter: ReviewPresenter
    private lateinit var adapter: DetailMovieAdapter

    private var page = 1
    private var totalPage = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (intent.extras != null) {
            val bundle = intent.extras
            itemMovie = bundle!!.getParcelable("itemMovie")!!
            setData()
        }

        presenter = ReviewPresenter(this)
        setBackButtonListener()
        setAdapter()
        setSwipeListener()
    }

    override fun onResume() {
        super.onResume()
        presenter.getReview(page, itemMovie.id?.toString() ?: "1")
    }

    override fun onPause() {
        super.onPause()
        presenter.disposeThis()
    }

    override fun callFinished(model: ReviewModel?) {
        totalPage = model?.totalPages ?: 0
        if (model?.results != null) {
            if (model.results.size > 0) {
                showData()
                adapter.setNewData(model.results)
            } else {
                noData()
            }
        } else {
            noData()
        }
    }

    override fun showError(msg: String?) {
        Log.d("Main", "showError ${msg ?: ""}")
        if (msg != null) {
            if (msg.contains("500")) {
                showToast(applicationContext, resources.getString(R.string.error_500))
            } else {
                showToast(applicationContext, msg)
            }
        }
    }

    override fun showLoading() {
        binding.swipeRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private fun setAdapter() {
        binding.rvReview.layoutManager = LinearLayoutManager(applicationContext)
        adapter = DetailMovieAdapter(this, mutableListOf())
        binding.rvReview.adapter = adapter
    }

    private fun setData() {
        ImageUtils().loadImage(this, binding.ivImage, itemMovie.posterPath)
        binding.tvTitleToolbar.text = itemMovie.title ?: "Detail"
        binding.tvTitle.text = itemMovie.title ?: "-"
        binding.tvReleasedDate.text =
            DateFormatterUtils().getDateFormatting4(this, itemMovie.releaseDate ?: "")
        binding.tvDescription.text = itemMovie.overview ?: "-"
    }

    private fun setSwipeListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.getReview(page, itemMovie.id?.toString() ?: "1")
        }
    }

    fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun showData() {
        binding.tvInfoReview.visibility = View.GONE
        binding.rvReview.visibility = View.VISIBLE
    }

    private fun noData() {
        binding.tvInfoReview.visibility = View.VISIBLE
        binding.rvReview.visibility = View.GONE
    }
}