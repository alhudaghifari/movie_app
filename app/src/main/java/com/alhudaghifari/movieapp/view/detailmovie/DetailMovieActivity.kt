package com.alhudaghifari.movieapp.view.detailmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alhudaghifari.movieapp.databinding.ActivityDetailMovieBinding
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.utils.DateFormatterUtils
import com.alhudaghifari.movieapp.utils.ImageUtils

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var itemMovie: ItemMovie;
    private lateinit var binding: ActivityDetailMovieBinding

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
        setBackButtonListener()
    }

    private fun setData() {
        ImageUtils().loadImage(this, binding.ivImage, itemMovie.posterPath)
        binding.tvTitleToolbar.text = itemMovie.title ?: "Detail"
        binding.tvTitle.text = itemMovie.title ?: "-"
        binding.tvReleasedDate.text =
            DateFormatterUtils().getDateFormatting4(this, itemMovie.releaseDate ?: "")
        binding.tvDescription.text = itemMovie.overview ?: "-"
    }

    fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }
}