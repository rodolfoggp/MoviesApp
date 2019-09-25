package com.rodolfogusson.testepag.infrastructure.application

import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoritesModule  = module {
    viewModel { FavoritesViewModel() }
}