package com.example.exercise602_catagentsrecyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise602_catagentsrecyclerview.models.CatBreed
import com.example.exercise602_catagentsrecyclerview.models.CatModel
import com.example.exercise602_catagentsrecyclerview.models.Gender


private val FEMALE_SYMBOL by lazy { HtmlCompat.fromHtml("&#9793;", HtmlCompat.FROM_HTML_MODE_LEGACY) }
private val MALE_SYMBOL by lazy { HtmlCompat.fromHtml("&#9794;", HtmlCompat.FROM_HTML_MODE_LEGACY) }
private const val UNKNOWN_SYMBOL = "?"

class CatViewHolder(containerView:View, private val imageLoader: ImageLoader): RecyclerView.ViewHolder(containerView) {

    private val catImage: ImageView by lazy { containerView.findViewById(R.id.iv_cat_photo) }
    private val catName: TextView by lazy { containerView.findViewById(R.id.tv_cat_name) }
    private val catBreed: TextView by lazy { containerView.findViewById(R.id.tv_cat_breed) }
    private val catBio: TextView by lazy { containerView.findViewById(R.id.tv_cat_biography) }
    private val catGender: TextView by lazy { containerView.findViewById(R.id.tv_cat_gender) }

    fun bind(catModel: CatModel){
        imageLoader.loadImage(catImage, catModel.imageUrl)

        catName.text = catModel.name
        catBio.text = catModel.biography
        catBreed.text = when (catModel.breed){
            CatBreed.AmericanCurl -> "Americal Curl"
            CatBreed.BalineseJavanese -> "Balinese Javanese"
            CatBreed.ExoticShorthair -> "Exotic shorthair"
        }
        catGender.text = when (catModel.gender){
            Gender.Female -> FEMALE_SYMBOL
            Gender.Male -> MALE_SYMBOL
            Gender.Unknown -> UNKNOWN_SYMBOL
        }
    }

}