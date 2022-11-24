package com.example.activity601_recipebook.viewholder

import android.view.View
import android.widget.TextView
import com.example.activity601_recipebook.R
import com.example.activity601_recipebook.ListItemAdapter
import com.example.activity601_recipebook.models.ListItemModel

class RecipeViewHolder(private val containerView: View, private val onClickListener: OnClickListener):
    ListItemViewHolder(containerView) {

    //hold references to views
    private val recipeTitle: TextView by lazy { containerView.findViewById(R.id.tv_title) }
    private val recipeDesc: TextView by lazy { containerView.findViewById(R.id.tv_desc) }

    //bind the values
    override fun bind(recipe: ListItemModel) {
        require(recipe is ListItemModel.RecipeModel){
            "Expected ListItemModel.Recipe"
        }

        containerView.setOnClickListener {
            onClickListener.onClick(recipe)
        }

        recipeTitle.text = recipe.title
        recipeDesc.text = recipe.description
    }

    interface OnClickListener {
        fun onClick(recipe: ListItemModel.RecipeModel)
    }



}