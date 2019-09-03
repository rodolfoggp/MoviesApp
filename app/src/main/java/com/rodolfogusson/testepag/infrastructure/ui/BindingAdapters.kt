package com.rodolfogusson.testepag.infrastructure.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    Picasso
        .get()
        .load("https://image.tmdb.org/t/p/w185$url")
        .into(imageView)
}