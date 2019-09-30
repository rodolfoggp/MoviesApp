package com.rodolfogusson.testepag.view.details

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.databinding.ActivityDetailsBinding
import com.rodolfogusson.testepag.infrastructure.ui.UIUtil
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModel
import kotlinx.android.synthetic.main.activity_details.*

abstract class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    lateinit var viewModel: DetailsViewModel

    abstract fun getViewModel(id: Int): DetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)
        binding.lifecycleOwner = this

        val id = intent.getIntExtra(movieIdKey, 0)
        viewModel = getViewModel(id)
        binding.viewModel = viewModel

        observeMovie()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteButton.setOnClickListener { viewModel.onFavoriteButtonClicked() }
    }

    private fun observeMovie() {
        viewModel.isFavorite.observe(this, Observer { isFavorite ->
            if (isFavorite) {
                favoriteIcon.setImageDrawable(
                    UIUtil.coloredDrawable(
                        this,
                        R.drawable.favorites,
                        R.color.colorAccent
                    )
                )
                favoriteText.text = getString(R.string.is_favorite)
            } else {
                favoriteIcon.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.not_favorite
                    )
                )
                favoriteText.text = getString(R.string.add_to_favorite)
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return true
    }

    companion object {
        const val movieIdKey = "MOVIE_ID_KEY"
    }
}
