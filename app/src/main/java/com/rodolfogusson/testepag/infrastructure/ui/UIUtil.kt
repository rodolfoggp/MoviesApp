package com.rodolfogusson.testepag.infrastructure.ui

import android.content.Context
import androidx.core.content.ContextCompat.getColor
import com.rodolfogusson.testepag.model.Genre
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class UIUtil {
    companion object {
        @JvmStatic
        fun genresNames(genres: List<Genre>) =
            genres.joinToString(separator = " | ") { genre -> genre.name }

        @JvmStatic
        fun fullDateText(date: LocalDate): String =
            DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy").format(date)

        @JvmStatic
        fun voteAverageString(average: Double) = "$average / 10"

        @JvmStatic
        fun voteCountString(count: Int) = "$count"
    }
}