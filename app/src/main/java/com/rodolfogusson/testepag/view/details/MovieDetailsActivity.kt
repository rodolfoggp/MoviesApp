package com.rodolfogusson.testepag.view.details

import androidx.lifecycle.ViewModelProviders
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModel
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModelFactory
import com.rodolfogusson.testepag.viewmodel.details.moviedetails.MovieDetailsViewModel

class MovieDetailsActivity : DetailsActivity() {
    override fun getViewModel(id: Int): DetailsViewModel {
        return ViewModelProviders.of(
            this,
            DetailsViewModelFactory(this, id)
        )
            .get(MovieDetailsViewModel::class.java)
    }
}