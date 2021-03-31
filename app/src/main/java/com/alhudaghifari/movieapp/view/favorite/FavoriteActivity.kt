package com.alhudaghifari.movieapp.view.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.databinding.ActivityDetailReviewBinding
import com.alhudaghifari.movieapp.databinding.ActivityFavoriteBinding

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setBackButtonListener()
    }

    fun setBackButtonListener() {
        binding.ivBackButton.setOnClickListener {
            finish()
        }
    }
}