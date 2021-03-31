package com.alhudaghifari.movieapp.utils

import android.content.Context
import android.widget.ImageView
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.presenter.ApiConstant
import com.bumptech.glide.Glide

class ImageUtils {
    fun loadImage(context: Context, ivImage: ImageView, posterPath: String?) {
        var path = ""
        if (posterPath != null) {
            if (posterPath.contains("/")) {
                path = posterPath
            } else {
                path = "/${posterPath}"
            }
        }

        val imgUrl = ApiConstant().imgServer + path

        Glide
            .with(context)
            .load(imgUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(ivImage);
    }
}