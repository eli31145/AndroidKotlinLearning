package com.example.activity501_weatherdisplay.model

import android.widget.ImageView
import com.squareup.moshi.Json

data class WeatherData(
    @field:Json(name = "main") val status: String,
    val description: String,
    val icon: String
)
