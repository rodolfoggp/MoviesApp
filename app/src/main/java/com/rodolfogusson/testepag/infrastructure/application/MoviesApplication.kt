package com.rodolfogusson.testepag.infrastructure.application

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.core.context.startKoin

class MoviesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        startKoin {
            modules(favoritesModule)
        }
    }
}