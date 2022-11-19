package com.example.exercise501_catagentprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.example.exercise501_catagentprofile.api.TheCatApiService
import com.example.exercise501_catagentprofile.model.ImageResultData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory
//import retrofit2.converter.scalars.ScalarsConverterFactory.create

class MainActivity : AppCompatActivity() {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            //.addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private val theCatApiService by lazy { retrofit.create(TheCatApiService::class.java) }
    private val serverResponseView: TextView by lazy { findViewById(R.id.main_server_response) }
    private val profileImageView: ImageView by lazy { findViewById(R.id.main_profile_image)}

    private val imageLoader:ImageLoader by lazy { GlideImageLoader(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getCatImageResponse()
    }

    private fun getCatImageResponse(){
        val call = theCatApiService.searchImages(1, "full")
        call.enqueue(object: Callback<List<ImageResultData>>{
            override fun onResponse(call: Call<List<ImageResultData>>, response: Response<List<ImageResultData>>) {
                if (response.isSuccessful){
                    //serverResponseView.text = response.body()
                    val imageResults = response.body()
                    val firstImageUrl = imageResults?.firstOrNull()?.imageUrl ?: "No URL"
                    if (firstImageUrl.isNotBlank()){
                        imageLoader.loadImage(firstImageUrl, profileImageView)
                    } else {
                        Log.d("MainActivity", "Missing Image URL")
                    }
                    serverResponseView.text = "ImageURL: $firstImageUrl"
                } else {
                    Log.e("MainActivity", "Failed to get search results\n ${response.errorBody()?.string() ?: ""}")
                }
            }

            override fun onFailure(call: Call<List<ImageResultData>>, t: Throwable) {
                Log.e("MainActivity", "Failed to get search results", t)
            }
        })
    }

}