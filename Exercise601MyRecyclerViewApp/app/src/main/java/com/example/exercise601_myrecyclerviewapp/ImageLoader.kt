package com.example.exercise601_myrecyclerviewapp

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageUrl: String, imageView: ImageView)
}