package com.example.exercise602_catagentsrecyclerview

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

class GlideImageLoader(private val context: Context):ImageLoader {

    override fun loadImage(imageView: ImageView, imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .centerInside()
            .into(imageView)
    }
}