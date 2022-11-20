package com.example.activity501_weatherdisplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.activity501_weatherdisplay.api.WeatherApiService
import com.example.activity501_weatherdisplay.model.WeatherData
import com.example.activity501_weatherdisplay.model.WeatherResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val weatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }

    private val textviewCity: TextView by lazy { findViewById(R.id.tv_city) }
    private val textviewDesc: TextView by lazy { findViewById(R.id.tv_description) }
    private val textviewWeather: TextView
        get() = findViewById(R.id.tv_weather)
    private val imageviewLogo: ImageView by lazy { findViewById(R.id.iv_logo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCityWeatherResponse()
    }

    private fun getCityWeatherResponse(){
        val call = weatherApiService.getCityWeather("singapore", "b78e41f5dcce4ab8dd822a277b9ff727")
        call.enqueue(object : Callback<WeatherResponseData> {
            override fun onFailure(call: Call<WeatherResponseData>, t: Throwable) {
                Log.e("MainActivity", "Weather information failed to retrieve", t)
            }

            override fun onResponse(
                call: Call<WeatherResponseData>,
                response: Response<WeatherResponseData>
            ) {
                if (response.isSuccessful){
                    response.body()?.let {
                            validResponse -> handleValidResponse(validResponse)
                    } ?: Unit

                } else {
                    Log.e("MainActivity", "response unsuccessful")
                }
            }
        })


    }

    private fun handleValidResponse(response: WeatherResponseData){
        textviewCity.text = response.cityName

        response.weather.firstOrNull()?.let { weather ->
            textviewWeather.text = weather.status
            textviewDesc.text = weather.description
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/${weather.icon}@2x.png")
                .centerInside()
                .into(imageviewLogo)
        }
    }

}