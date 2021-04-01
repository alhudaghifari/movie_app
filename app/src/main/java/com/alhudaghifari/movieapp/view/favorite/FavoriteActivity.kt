package com.alhudaghifari.movieapp.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityDetailReviewBinding
import com.alhudaghifari.movieapp.databinding.ActivityFavoriteBinding
import com.alhudaghifari.movieapp.db.DatabaseBuilder
import com.alhudaghifari.movieapp.db.DatabaseHelperImpl
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.presenter.favorite.FavoriteDbHelper
import com.alhudaghifari.movieapp.utils.Status
import com.alhudaghifari.movieapp.view.detailmovie.DetailMovieActivity
import com.alhudaghifari.movieapp.view.home.HomeAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteDbHelper
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setBackButtonListener()
        setAdapter()
        setViewModel()
    }

    override fun onResume() {
        super.onResume()
        getDataFavorite()
    }

    private fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setAdapter() {
        binding.rvFavorite.layoutManager = LinearLayoutManager(applicationContext)
        adapter = FavoriteAdapter(applicationContext, mutableListOf())
        adapter.setOnMovieClickListener(object: FavoriteAdapter.OnMovieClickListener {
            override fun onClick(position: Int, itemMovie: ItemMovie) {
                val intent = Intent(this@FavoriteActivity, DetailMovieActivity::class.java)
                intent.putExtra("itemMovie", itemMovie)
                startActivity(intent)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    private fun setViewModel() {
        viewModel = FavoriteDbHelper( DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))
        viewModel.getMovies().observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { favMovies ->
                        val listMovie: MutableList<ItemMovie> = mutableListOf()
                        for (movie in favMovies) {
                            Log.d("FavoriteAct", "get movie kuu $movie")
                            val item = ItemMovie(
                                id = movie.id,
                                originalTitle = movie.title,
                                releaseDate = movie.releasedDate,
                                overview = movie.overview,
                                posterPath = movie.posterPath,
                            )
                            listMovie.add(item)
                        }
                        adapter.setNewData(listMovie)
                    }
                    binding.rvFavorite.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvFavorite.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun getDataFavorite() {
        viewModel.fetchMovies()
    }
}