package com.rodolfogusson.testepag.infrastructure.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.rodolfogusson.testepag.viewmodel.favorites.FavoritesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            modules(favoritesModule)
        }
    }
}