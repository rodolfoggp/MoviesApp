package com.rodolfogusson.testepag.viewmodel.movieslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class MoviesListViewModel: ViewModel() {
    lateinit var movies: LiveData<String>
}