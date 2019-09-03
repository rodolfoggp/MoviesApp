package com.rodolfogusson.testepag.infrastructure.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rodolfogusson.testepag.model.Movie

class MoviesRepository {

    private val movies = MutableLiveData<List<Movie>>()

    fun getMovies(): LiveData<List<Movie>> {
        movies.value = listOf(
            Movie(
                1,
                "Filme 1",
                "2019-09-05",
                "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
                7.4,
                10,
                "alsfiae alisejflaiejflaisejlf iajeli"
            ),
            Movie(
                2,
                "Filme 2",
                "2019-09-05",
                "/kRLIHHf4KrwYFInU08akbzSGCrW.jpg",
                8.1,
                1,
                "afaswefasiejçlfasiejfli slei lsiejlsijlsie"
            )
        )
        return movies
    }
}