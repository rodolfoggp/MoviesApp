package com.rodolfogusson.testepag.view.favorites.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rodolfogusson.testepag.databinding.MoviesListItemBinding
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.view.favorites.adapter.FavoritesAdapter.FavoriteHolder

class FavoritesAdapter(var data: List<Movie> = emptyList()) : Adapter<FavoriteHolder>() {

    lateinit var clickListener: (Movie) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MoviesListItemBinding.inflate(inflater)
        return FavoriteHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) =
        holder.bind(data[position], clickListener)

    class FavoriteHolder(private val binding: MoviesListItemBinding) : ViewHolder(binding.root) {
        fun bind(movie: Movie, clickListener: (Movie) -> Unit) {
            binding.movie = movie
            binding.root.setOnClickListener { clickListener(movie) }
            binding.executePendingBindings()
        }
    }
}