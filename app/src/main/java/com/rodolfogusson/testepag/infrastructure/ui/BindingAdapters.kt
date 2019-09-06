package com.rodolfogusson.testepag.infrastructure.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.rodolfogusson.testepag.R
import com.squareup.picasso.Picasso
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Picasso
        .get()
        .load("https://image.tmdb.org/t/p/w185$url")
        .into(imageView)
}

@BindingAdapter("dateText")
fun setDateText(textView: TextView, date: LocalDate?) {
    val dateText = date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val text = textView.resources.getString(R.string.release_date, dateText)
    textView.text = text
}
