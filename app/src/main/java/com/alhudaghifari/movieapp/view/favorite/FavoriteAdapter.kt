package com.alhudaghifari.movieapp.view.favorite

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
import com.alhudaghifari.movieapp.utils.DateFormatterUtils
import com.alhudaghifari.movieapp.utils.ImageUtils
import com.alhudaghifari.movieapp.view.detailmovie.DetailMovieActivity

class FavoriteAdapter(internal var context: Context?, internal var data: MutableList<ItemMovie>) :
    RecyclerView.Adapter<FavoriteAdapter.ArticleViewHolder>() {

    private var onMovieClickListener: OnMovieClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val item = data[position]

        holder.tvTitle.text = item.originalTitle ?: "-"
        holder.tvReleasedDate.text =
            DateFormatterUtils().getDateFormatting4(context!!, item.releaseDate ?: "")
        holder.tvDescription.text = item.overview ?: "-"

        ImageUtils().loadImage(context!!, holder.ivImage, item.posterPath)

        holder.conlayParentMovie.setOnClickListener {
           if (onMovieClickListener != null) {
               onMovieClickListener!!.onClick(position, item)
           }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun addNewData(data: List<ItemMovie>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun setNewData(data: MutableList<ItemMovie>) {
        this.data = data
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

    interface OnMovieClickListener {
        fun onClick(position: Int, itemMovie: ItemMovie)
    }

    fun setOnMovieClickListener(onMovieClickListener: OnMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener
    }
}