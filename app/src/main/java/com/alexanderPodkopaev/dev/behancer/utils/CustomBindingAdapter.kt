package com.alexanderPodkopaev.dev.behancer.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("bind:imageUrl")
fun loadImage(imageView: ImageView, urlImage: String) {
    Picasso.get()
            .load(urlImage)
            .fit()
            .into(imageView)
}