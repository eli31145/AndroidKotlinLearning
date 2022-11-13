package com.example.exercise303_dualpanelayouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class DetailActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //val starSignId = intent.extras?.getInt(STAR_SIGN_ID, 0) ?:0
        val starSignId = intent?.getIntExtra(STAR_SIGN_ID, 0) ?: 0

        val detailFragment = supportFragmentManager.findFragmentById(R.id.star_sign_detail) as DetailFragment
        detailFragment.setStarSignData(starSignId)
    }
}