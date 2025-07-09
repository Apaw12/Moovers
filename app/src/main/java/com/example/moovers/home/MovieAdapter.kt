package com.example.moovers.home

import com.example.moovers.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moovers.model.Movie

class MovieAdapter(
    private val context: Context,
    private val movies: List<Movie>,
    private val onMovieClick: (Movie) -> Unit
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagePoster: ImageView = itemView.findViewById(R.id.ivMoviePoster)
        val tvTitle: TextView = itemView.findViewById(R.id.tvMovieTitle)
        val tvDate: TextView = itemView.findViewById(R.id.tvReleaseDate)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_movie_card, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.imagePoster.setImageResource(movie.posterResId)
        holder.tvTitle.text = movie.title
        holder.tvDate.text = movie.releaseDate
        holder.ratingBar.rating = movie.rating
        holder.itemView.setOnClickListener { onMovieClick(movie) }
    }

    override fun getItemCount(): Int = movies.size
}
