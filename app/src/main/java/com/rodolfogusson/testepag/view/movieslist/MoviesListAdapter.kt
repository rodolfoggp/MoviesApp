package com.rodolfogusson.testepag.view.movieslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.rodolfogusson.testepag.databinding.MoviesListItemBinding
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.view.movieslist.MoviesListAdapter.MovieHolder


class MoviesListAdapter(private val data: List<Movie>) : Adapter<MovieHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MoviesListItemBinding.inflate(inflater)
        return MovieHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) = holder.bind(data[position])

    class MovieHolder(private val binding: MoviesListItemBinding) : ViewHolder(binding.root) {
        fun bind(movie: Movie) {
            binding.movie = movie
        }
    }
}