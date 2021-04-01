package com.alhudaghifari.movieapp.view.detailmovie

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityDetailMovieBinding
import com.alhudaghifari.movieapp.db.DatabaseBuilder
import com.alhudaghifari.movieapp.db.DatabaseHelperImpl
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.model.ItemReview
import com.alhudaghifari.movieapp.model.ReviewModel
import com.alhudaghifari.movieapp.presenter.favorite.FavoriteDbHelper
import com.alhudaghifari.movieapp.presenter.review.ReviewInterface
import com.alhudaghifari.movieapp.presenter.review.ReviewPresenter
import com.alhudaghifari.movieapp.utils.DateFormatterUtils
import com.alhudaghifari.movieapp.utils.ImageUtils
import com.alhudaghifari.movieapp.utils.Status
import com.alhudaghifari.movieapp.utils.showToast

class DetailMovieActivity : AppCompatActivity(), ReviewInterface {

    private lateinit var itemMovie: ItemMovie;
    private lateinit var binding: ActivityDetailMovieBinding
    private lateinit var presenter: ReviewPresenter
    private lateinit var adapter: DetailMovieAdapter
    private lateinit var viewModel: FavoriteDbHelper

    private var page = 1
    private var totalPage = 0

    private var statusFavorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        presenter = ReviewPresenter(this)
        setBackButtonListener()
        setViewModel()
        setAdapter()
        setSwipeListener()

        if (intent.extras != null) {
            val bundle = intent.extras
            itemMovie = bundle!!.getParcelable("itemMovie")!!
            setData()
            setButtonFavoriteListener()
        }
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

    private fun setViewModel() {
        viewModel = FavoriteDbHelper( DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))
        viewModel.getSingleMovie().observe(this, {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { movieList ->
                        if (movieList.size > 0) {
                            if (movieList[0].id == itemMovie.id) {
                                binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                                statusFavorite = true
                            }
                        }
                    }
                }
                Status.LOADING -> {
                    Log.d("DetailMovieAct", "getSingleMovie loading")
                }
                Status.ERROR -> {
                    Log.e("DetailMovieAct", "getSingleMovie error : ${it.message}")
                }
            }
        })
        viewModel.getStatusFavorite().observe(this,  {
            when(it.status) {
                Status.SUCCESS -> {
                    it.data?.let { status ->
                    if (status) {
                        binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_24_red)
                        statusFavorite = true
                    } else {
                        binding.ivFavorite.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                        statusFavorite = false
                    }
                    }
                }
                Status.LOADING -> {
                    Log.d("DetailMovieAct", "getStatusFavorite loading")
                }
                Status.ERROR -> {
                    Log.e("DetailMovieAct", "getStatusFavorite error : ${it.message}")
                }
            }
        })
    }

    private fun setData() {
        ImageUtils().loadImage(this, binding.ivImage, itemMovie.posterPath)
        binding.tvTitleToolbar.text = itemMovie.title ?: "Detail"
        binding.tvTitle.text = itemMovie.title ?: "-"
        binding.tvReleasedDate.text =
            DateFormatterUtils().getDateFormatting4(this, itemMovie.releaseDate ?: "")
        binding.tvDescription.text = itemMovie.overview ?: "-"
        viewModel.fetchMovieById(itemMovie.id!!)
    }

    private fun setSwipeListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.getReview(page, itemMovie.id?.toString() ?: "1")
        }
    }

    private fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setButtonFavoriteListener() {
        binding.ivFavorite.setOnClickListener {
            if (statusFavorite) {
                viewModel.deleteFavorite(itemMovie)
            } else {
                viewModel.addFavorite(itemMovie)
            }
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