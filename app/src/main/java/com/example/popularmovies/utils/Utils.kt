package com.example.popularmovies.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions


fun ImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .apply(
            RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
        )
        .into(this)
}


@BindingAdapter("android:ImageUrl")
fun loadImageIntoView(view: ImageView, url: String?) {
    view.loadImage(url)
}