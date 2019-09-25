package com.rodolfogusson.testepag.viewmodel.favorites.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.favorites.adapter.FavoritesAdapter.FavoriteHolder

class FavoritesAdapter: Adapter<FavoriteHolder>() {

    lateinit var clickListener: (Movie) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    class FavoriteHolder(v: View): ViewHolder(v)
}