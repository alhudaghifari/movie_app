package com.alhudaghifari.movieapp.view.detailmovie

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.alhudaghifari.movieapp.R
import com.alhudaghifari.movieapp.model.ItemMovie
import com.alhudaghifari.movieapp.model.ItemReview
import com.alhudaghifari.movieapp.presenter.ApiConstant
import com.alhudaghifari.movieapp.view.home.HomeAdapter
import com.bumptech.glide.Glide

class DetailMovieAdapter (internal var context: Context?, internal var data: MutableList<ItemReview>) :
    RecyclerView.Adapter<DetailMovieAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_review, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = data[position]

        var path = ""
        if (item.authorDetails?.avatarPath != null) {
            if (item.authorDetails.avatarPath.contains(ApiConstant().imgReviewServer)) {
                path = item.authorDetails.avatarPath
            } else {
                path = "${ApiConstant().imgReviewServer}${item.authorDetails.avatarPath}"
            }
        }

        Glide
            .with(context!!)
            .load(path)
            .centerCrop()
            .placeholder(R.drawable.ic_baseline_image_24)
            .into(holder.ivImage)

        holder.tvName.text = item.author ?: "-"
        holder.tvUsername.text = item.authorDetails?.username ?: "-"
        val rating = item.authorDetails?.rating?.toString() ?: "-"
        val ratingText = "Rating : $rating"
        holder.tvRating.text = ratingText
        holder.tvReview.text = item.content ?: "-"

        holder.conlayReview.setOnClickListener {
            val intent = Intent(context, DetailReviewActivity::class.java)
            intent.putExtra("review",item.content ?: "-")
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addNewData(data: List<ItemReview>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setNewData(data: MutableList<ItemReview>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ArticleViewHolder(mViewContainer: View) : RecyclerView.ViewHolder(mViewContainer) {
        internal var conlayReview: ConstraintLayout

        internal var ivImage: ImageView
        internal var tvName: TextView
        internal var tvUsername: TextView
        internal var tvRating: TextView
        internal var tvReview: TextView

        init {
            conlayReview = mViewContainer.findViewById(R.id.conlayReview)
            ivImage = mViewContainer.findViewById(R.id.ivImage)
            tvName = mViewContainer.findViewById(R.id.tvName)
            tvUsername = mViewContainer.findViewById(R.id.tvUsername)
            tvRating = mViewContainer.findViewById(R.id.tvRating)
            tvReview = mViewContainer.findViewById(R.id.tvReview)
        }
    }

}