package com.rodolfogusson.testepag.viewmodel.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodolfogusson.testepag.model.Movie

class FavoritesViewModel: ViewModel() {
    val favorites: LiveData<List<Movie>> = MutableLiveData()
}