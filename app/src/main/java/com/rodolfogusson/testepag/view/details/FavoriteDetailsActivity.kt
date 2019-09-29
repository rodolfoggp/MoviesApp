package com.rodolfogusson.testepag.view.details

import androidx.lifecycle.ViewModelProviders
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModel
import com.rodolfogusson.testepag.viewmodel.details.DetailsViewModelFactory
import com.rodolfogusson.testepag.viewmodel.details.favoritedetails.FavoriteDetailsViewModel

class FavoriteDetailsActivity : DetailsActivity() {
    override fun getViewModel(id: Int): DetailsViewModel {
        return ViewModelProviders.of(
            this,
            DetailsViewModelFactory(this, id)
        )
            .get(FavoriteDetailsViewModel::class.java)
    }
}