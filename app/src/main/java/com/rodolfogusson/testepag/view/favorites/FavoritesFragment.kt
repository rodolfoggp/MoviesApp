package com.rodolfogusson.testepag.view.favorites

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.rodolfogusson.testepag.databinding.FragmentFavoritesBinding
import com.rodolfogusson.testepag.model.Movie
import com.rodolfogusson.testepag.view.moviesdetails.MovieDetailsActivity
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import com.rodolfogusson.testepag.viewmodel.favorites.adapter.FavoritesAdapter
import kotlinx.android.synthetic.main.fragment_favorites.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment : Fragment() {

    val vm: FavoritesViewModel by viewModel()
    lateinit var binding: FragmentFavoritesBinding
    val adapter = FavoritesAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)
        binding.viewModel = vm
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupLayout()
        registerObservers()
    }

    private fun setupLayout() {
        adapter.clickListener = ::onMovieClicked
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this.activity)
    }

    private fun registerObservers() {
        vm.favorites.observe(this, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })
    }

    private fun onMovieClicked(movie: Movie) {
        val intent = Intent(activity, MovieDetailsActivity::class.java)
        intent.putExtra(MovieDetailsActivity.movieIdKey, movie.id)
        startActivity(intent)
    }
}
