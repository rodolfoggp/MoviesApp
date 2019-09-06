package com.rodolfogusson.testepag.infrastructure

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

class MoviesApplication : Application() {
    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}