package com.rodolfogusson.testepag.infrastructure.ui

import androidx.appcompat.widget.AppCompatButton
import com.rodolfogusson.testepag.model.Genre
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import android.content.Context
import androidx.core.content.ContextCompat
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable

class UIUtil {
    companion object {
        @JvmStatic
        fun genresNames(genres: List<Genre>) =
            genres.joinToString(separator = " | ") { genre -> genre.name }

        @JvmStatic
        fun fullDateText(date: LocalDate) =
            DateTimeFormatter.ofPattern("dd 'de' MMMM 'de' yyyy").format(date)

        @JvmStatic
        fun voteAverageString(average: Double) = "$average / 10"

        @JvmStatic
        fun voteCountString(count: Int) = "$count"

        fun setButtonColor(color: Int, button: AppCompatButton, context: Context) {
            when (val background = button.background) {
                is ShapeDrawable -> background.paint.color =
                    ContextCompat.getColor(context, color)
                is GradientDrawable -> background.setColor(
                    ContextCompat.getColor(
                        context,
                        color
                    )
                )
                is ColorDrawable -> background.color =
                    ContextCompat.getColor(context, color)
            }
        }
    }
}