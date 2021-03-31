package com.alhudaghifari.movieapp.view.home

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.presenter.ApiConstant
import com.alhudaghifari.movieapp.utils.DateFormatterUtils
import com.bumptech.glide.Glide

class HomeAdapter(internal var context: Context?, internal var data: MutableList<ItemMovie>)  : RecyclerView.Adapter<HomeAdapter.ArticleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = data[position]

        holder.tvTitle.text = item.originalTitle ?: ""
        holder.tvReleasedDate.text = DateFormatterUtils().getDateFormatting4(context!!, item.releaseDate ?: "")
        holder.tvDescription.text = item.overview

        var path = ""
        if (item.posterPath != null) {
            if (item.posterPath.contains("/")) {
                path = item.posterPath
            } else {
                path = "/${item.posterPath}"
            }
        }

        val imgUrl = ApiConstant().imgServer + path

        Glide
            .with(context!!)
            .load(imgUrl)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.ivImage);
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addNewData(data:List<ItemMovie>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class ArticleViewHolder(mViewContainer: View) : RecyclerView.ViewHolder(mViewContainer) {
        internal var conlayParentMovie: ConstraintLayout

        internal var ivImage: ImageView
        internal var tvTitle: TextView
        internal var tvReleasedDate: TextView
        internal var tvDescription: TextView

        init {
            conlayParentMovie = mViewContainer.findViewById(R.id.conlayParentMovie)
            ivImage = mViewContainer.findViewById(R.id.ivImage)
            tvTitle = mViewContainer.findViewById(R.id.tvTitle)
            tvReleasedDate = mViewContainer.findViewById(R.id.tvReleasedDate)
            tvDescription = mViewContainer.findViewById(R.id.tvDescription)
        }
    }

}