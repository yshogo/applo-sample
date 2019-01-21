package com.example.shogoyamada.applo_sample_app

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide


@BindingAdapter("app:url")
fun bindingNetworkImage(view: ImageView, url: String?) {
    val networkImage = url?: return
    Glide.with(view.context)
        .load(networkImage)
        .into(view)
}