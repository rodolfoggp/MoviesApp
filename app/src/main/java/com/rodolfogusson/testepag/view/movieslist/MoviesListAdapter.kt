package com.rodolfogusson.testepag.view.movieslist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.view.movieslist.MoviesListAdapter.MovieHolder


class MoviesListAdapter(private val data: List<String>) : Adapter<MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.movies_list_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) = holder.bind(data[position])

    class MovieHolder(itemView: View) : ViewHolder(itemView) {
        fun bind(str: String) {
            val tv = itemView.findViewById<TextView>(R.id.textView)
            tv.text = str
        }
    }
}