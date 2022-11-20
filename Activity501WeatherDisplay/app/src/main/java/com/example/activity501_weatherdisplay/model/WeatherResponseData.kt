package com.example.activity501_weatherdisplay.model

import com.squareup.moshi.Json

data class WeatherResponseData(
    @field:Json(name = "name") val cityName: String,
    val weather: List<WeatherData>
)
