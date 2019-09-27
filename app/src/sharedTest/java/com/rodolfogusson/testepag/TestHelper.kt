package com.rodolfogusson.testepag

import com.rodolfogusson.testepag.model.Genre
import com.rodolfogusson.testepag.model.Movie
import org.threeten.bp.LocalDate

class TestHelper {

    val moviesList = listOf(
        Movie(
            1,
            "Filme 1",
            "Descrição 1",
            LocalDate.parse("2019-05-12"),
            "/wF6SNPcUrTKFA4fOFfukm7zQ3ob.jpg",
            6.4,
            10
        ),
        Movie(
            2,
            "Filme 2",
            "Descrição 2",
            LocalDate.parse("2019-08-26"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            7.9,
            17
        ),
        Movie(
            3,
            "Filme 3",
            "Descrição 2",
            LocalDate.parse("2019-07-22"),
            "/foEOVg4HQl2VzKzTh27CAHmXyg.jpg",
            8.0,
            19
        )
    )

    val genresList = listOf(
        Genre(1, "A"),
        Genre(2, "B"),
        Genre(3, "C"),
        Genre(4, "D"),
        Genre(5, "E"),
        Genre(6, "F")
    )

    fun includeGenresInMovies() {
        moviesList[0].genres = listOf(genresList[0], genresList[1])
        moviesList[1].genres = listOf(genresList[2], genresList[3])
        moviesList[2].genres = listOf(genresList[4], genresList[5])
    }
}