package com.rodolfogusson.testepag.infrastructure.service.dto

import com.google.gson.annotations.SerializedName
import com.rodolfogusson.testepag.model.Movie

data class MoviesResponse(
    val results: List<Movie>,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)