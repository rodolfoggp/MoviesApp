package com.rodolfogusson.testepag.view.moviesdetails

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.databinding.ActivityMoviesDetailsBinding
import com.rodolfogusson.testepag.infrastructure.ui.UIUtil
import com.rodolfogusson.testepag.viewmodel.moviesdetails.MovieDetailsViewModel
import com.rodolfogusson.testepag.viewmodel.moviesdetails.MovieDetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_movies_details.*

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

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteButton.setOnClickListener {
            val unwrappedDrawable = AppCompatResources.getDrawable(this, R.drawable.favorites)
            val wrappedDrawable = DrawableCompat.wrap(unwrappedDrawable!!)
            DrawableCompat.setTint(wrappedDrawable, ContextCompat.getColor(this, R.color.colorAccent))
            favoriteMark.setImageDrawable(wrappedDrawable)
            favoriteText.text = "FAVORITO"
        }

    }

    private fun observeMovie() {
        viewModel.movie.observe(this, Observer { movie ->
            if (movie.overview.isBlank()) overview.visibility = View.GONE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {
        const val movieIdKey = "MOVIE_ID_KEY"
    }
}
