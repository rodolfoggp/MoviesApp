package com.rodolfogusson.testepag.view.favorites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodolfogusson.testepag.databinding.FragmentFavoritesBinding
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import com.rodolfogusson.testepag.view.favorites.adapter.FavoritesAdapter
import com.rodolfogusson.testepag.view.details.DetailsActivity
import com.rodolfogusson.testepag.view.details.FavoriteDetailsActivity
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModelFactory
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    private lateinit var viewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private val adapter = FavoritesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        val context: Context = context ?: return null
        viewModel = ViewModelProviders.of(this, FavoritesViewModelFactory(context))
            .get(FavoritesViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        adapter.clickListener = ::onMovieClicked
        recyclerViewFavorites.adapter = adapter
        recyclerViewFavorites.layoutManager = LinearLayoutManager(this.activity)
    }

    private fun registerObservers() {
        viewModel.favorites.observe(this, Observer {
            emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            recyclerViewFavorites.visibility = if (it.isNotEmpty()) View.VISIBLE else View.GONE

            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun onMovieClicked(movie: Movie) {
        val intent = Intent(activity, FavoriteDetailsActivity::class.java)
        intent.putExtra(DetailsActivity.movieIdKey, movie.id)
        startActivity(intent)
    }
}
