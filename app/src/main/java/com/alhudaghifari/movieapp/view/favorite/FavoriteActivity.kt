package com.alhudaghifari.movieapp.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityDetailReviewBinding
import com.alhudaghifari.movieapp.databinding.ActivityFavoriteBinding
import com.alhudaghifari.movieapp.db.DatabaseBuilder
import com.alhudaghifari.movieapp.db.DatabaseHelperImpl
import com.alhudaghifari.movieapp.presenter.favorite.FavoriteDbHelper
import com.alhudaghifari.movieapp.utils.Status

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var viewModel: FavoriteDbHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setBackButtonListener()
        setViewModel()
    }

    private fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }

    private fun setViewModel() {
        viewModel = FavoriteDbHelper( DatabaseHelperImpl(DatabaseBuilder.getInstance(applicationContext)))
        viewModel.fetchMovies()
        viewModel.getMovies().observe(this, Observer {
            when(it.status) {
                Status.SUCCESS -> {
//                    progressBar.visibility = View.GONE
                    it.data?.let { users -> binding.tvHalo.text = users.toString() }
//                    recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
//                    progressBar.visibility = View.VISIBLE
//                    recyclerView.visibility = View.GONE
                    Toast.makeText(this, "loading", Toast.LENGTH_LONG).show()
                }
                Status.ERROR -> {
                    //Handle Error
//                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })

    }
}