package com.example.exercise602_catagentsrecyclerview

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageView: ImageView, imageUrl: String)
}