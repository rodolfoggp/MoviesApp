package com.rodolfogusson.testepag.view.moviesdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.databinding.ActivityMoviesDetailsBinding
import com.rodolfogusson.testepag.viewmodel.moviesdetails.MovieDetailsViewModel
import com.rodolfogusson.testepag.viewmodel.moviesdetails.MovieDetailsViewModelFactory

class MovieDetailsActivity : AppCompatActivity() {

    lateinit var binding: ActivityMoviesDetailsBinding
    lateinit var viewModel: MovieDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies_details)

        val id = intent.getIntExtra(movieIdKey, 0)

        viewModel = ViewModelProviders.of(this, MovieDetailsViewModelFactory(id))
            .get(MovieDetailsViewModel::class.java)
        binding.viewModel = viewModel
        observeMovie()
    }

    private fun observeMovie() {
        viewModel.movie.observe(this, Observer {
            title = it.title
        })
    }

    companion object {
        const val movieIdKey = "MOVIE_ID_KEY"
    }
}
