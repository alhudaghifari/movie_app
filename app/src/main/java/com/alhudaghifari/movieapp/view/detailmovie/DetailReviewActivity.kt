package com.alhudaghifari.movieapp.view.detailmovie

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityDetailMovieBinding
import com.alhudaghifari.movieapp.databinding.ActivityDetailReviewBinding

class DetailReviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailReviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_review)
        binding = ActivityDetailReviewBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setBackButtonListener()

        if (intent.extras != null) {
            val bundle = intent.extras
            val review = bundle!!.getString("review", "-")
            binding.tvReview.text = review
        }
    }


    fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }
}