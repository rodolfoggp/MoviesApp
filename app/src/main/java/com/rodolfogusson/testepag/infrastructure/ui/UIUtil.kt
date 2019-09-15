package com.rodolfogusson.testepag.infrastructure.ui

import com.rodolfogusson.testepag.model.Genre

class UIUtil {
    companion object {
        @JvmStatic
        fun genresNames(genres: List<Genre>) =
            genres.joinToString(separator = " | ") { genre -> genre.name }
    }
}