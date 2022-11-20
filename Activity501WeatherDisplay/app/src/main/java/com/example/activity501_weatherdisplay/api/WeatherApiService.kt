package com.example.activity501_weatherdisplay.api

import com.example.activity501_weatherdisplay.model.WeatherData
import com.example.activity501_weatherdisplay.model.WeatherResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {
    @GET("weather")
    fun getCityWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String
    ): Call<WeatherResponseData>
}