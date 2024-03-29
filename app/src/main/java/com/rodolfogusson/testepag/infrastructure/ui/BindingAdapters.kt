package com.rodolfogusson.testepag.infrastructure.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.rodolfogusson.testepag.R
import com.rodolfogusson.testepag.infrastructure.service.BaseURL
import com.squareup.picasso.Picasso
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    url?.let {
        val baseUrl = BaseURL.MOVIES_IMAGES.url
        Picasso
            .get()
            .load("$baseUrl$url")
            .fit()
            .into(imageView)
    } ?: run {
        imageView.setImageResource(R.drawable.placeholder)
    }
}

@BindingAdapter("dateText")
fun setDateText(textView: TextView, date: LocalDate?) {
    val dateText = date?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val text = textView.resources.getString(R.string.release_date, dateText)
    textView.text = text
}