package com.rodolfogusson.testepag.infrastructure.service.dto

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    val results: List<MoviesResponseElement>,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int
)