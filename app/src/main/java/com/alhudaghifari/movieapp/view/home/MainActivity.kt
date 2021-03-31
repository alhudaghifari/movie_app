package com.alhudaghifari.movieapp.view.home

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityMainBinding
import com.alhudaghifari.movieapp.model.MovieListModel
import com.alhudaghifari.movieapp.presenter.movie_list.MovieListInterface
import com.alhudaghifari.movieapp.presenter.movie_list.MovieListPresenter
import com.alhudaghifari.movieapp.utils.showToast


class MainActivity : AppCompatActivity(), MovieListInterface {

    private val TAG = "MainActivity"
    private lateinit var presenter: MovieListPresenter
    private lateinit var adapter: HomeAdapter
    private lateinit var binding: ActivityMainBinding
    private var page = 1
    private var totalPage = 0
    private var isDataFromPagination = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(findViewById(R.id.toolbarMain))
        presenter = MovieListPresenter(this)

        setSwipeListener()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
        presenter.getPopular(page)
        isDataFromPagination = false
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onResume")
        presenter.disposeThis()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_favorite -> {
            //
            true
        }
        else -> {

            super.onOptionsItemSelected(item)
        }
    }

    override fun callFinished(model: MovieListModel) {
        Log.d(TAG, "callFinished")
        totalPage = model.totalPages ?: 0
        if (!isDataFromPagination) {
            if (model.results != null) {
                if (model.results.size > 0) {
                    showData()
                    binding.rvMovieMain.layoutManager = LinearLayoutManager(applicationContext)
                    adapter = HomeAdapter(applicationContext, model.results)
                    binding.rvMovieMain.adapter = adapter
                    setScrollListener()
                } else {
                    noData()
                }
            } else {
                noData()
            }
        } else {
            if (model.results != null) {
                adapter.addNewData(model.results)
            }
        }
        isDataFromPagination = false
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

    private fun setSwipeListener() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            page = 1
            presenter.getPopular(page)
        }
    }

    private fun setScrollListener() {
        binding.rvMovieMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE
                    && page < totalPage && !binding.swipeRefreshLayout.isRefreshing) {
                    isDataFromPagination = true
                    page++
                    presenter.getPopular(page)
                }
            }
        })
    }

    private fun showData() {
        binding.tvInfo.visibility = View.GONE
        binding.rvMovieMain.visibility = View.VISIBLE
    }

    private fun noData() {
        binding.tvInfo.visibility = View.VISIBLE
        binding.rvMovieMain.visibility = View.GONE
    }
}