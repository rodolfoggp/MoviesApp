package com.rodolfogusson.testepag.view.main.adapter

import com.rodolfogusson.testepag.model.Movie
import kotlin.properties.Delegates

class PaginatedMoviesAdapter : MoviesAdapter(),
    AutoUpdatableAdapter {
    override var data: List<Movie> by Delegates.observable(emptyList()) { _, old, new ->
        autoNotify(old, new) { o, n -> o.id == n.id }
    }
}
