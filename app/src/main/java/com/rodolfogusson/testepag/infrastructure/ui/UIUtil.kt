package com.rodolfogusson.testepag.infrastructure.ui

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
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

        fun coloredDrawable(context: Context, drawableId: Int, colorId: Int): Drawable? {
            AppCompatResources.getDrawable(context, drawableId)?.let {
                val wrappedDrawable = DrawableCompat.wrap(it)
                DrawableCompat.setTint(
                    wrappedDrawable,
                    ContextCompat.getColor(context, colorId)
                )
                return wrappedDrawable
            }
            return null
        }
    }
}