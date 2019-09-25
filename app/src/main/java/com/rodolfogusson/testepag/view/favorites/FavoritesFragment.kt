package com.rodolfogusson.testepag.view.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import com.rodolfogusson.testepag.viewmodel.favorites.adapter.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*

class FavoritesFragment : Fragment() {

    lateinit var viewModel: FavoritesViewModel
    val adapter = FavoritesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewModel = ViewModelProviders.of(this).get(FavoritesViewModel::class.java)
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupLayout()
    }

    private fun setupLayout() {
        adapter.clickListener = ::onMovieClicked
        recyclerView.adapter = adapter
    }

    private fun onMovieClicked(movie: Movie) {

    }
}
