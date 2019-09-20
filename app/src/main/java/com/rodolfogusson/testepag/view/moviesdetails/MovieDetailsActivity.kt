package com.rodolfogusson.testepag.view.moviesdetails

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ScaleDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.databinding.ActivityMoviesDetailsBinding
import com.rodolfogusson.testepag.infrastructure.ui.UIUtil
import com.rodolfogusson.testepag.viewmodel.moviesdetails.MovieDetailsViewModel
import com.rodolfogusson.testepag.viewmodel.moviesdetails.MovieDetailsViewModelFactory
import kotlinx.android.synthetic.main.activity_movies_details.*
import kotlinx.android.synthetic.main.movies_list_item.view.*
import androidx.core.graphics.drawable.DrawableCompat
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources



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
            DrawableCompat.setTint(wrappedDrawable, getColor(R.color.colorAccent))
            favoriteMark.setImageDrawable(wrappedDrawable)
            favoriteText.text = "FAVORITO"
        }

        /*favoriteButton.setOnClickListener {
            *//*favoriteButton.background = resources.getDrawable(R.drawable.round_green_button)
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds(resources.getDrawable(R.drawable.favorites), null, null, null)
            favoriteButton.text = "FAVORITO"*//*
        }*/
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
