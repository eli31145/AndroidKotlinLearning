package com.example.activity601_recipebook.viewholder

import android.view.View
import android.widget.TextView
import com.example.activity601_recipebook.R
import com.example.activity601_recipebook.models.ListItemModel

class FlavorViewHolder(private val containerView: View):ListItemViewHolder(containerView) {
    private val tvFlavor:TextView by lazy { containerView.findViewById(R.id.tv_flavor_title) }

    override fun bind(listItem: ListItemModel) {
        require(listItem is ListItemModel.Flavor){
            "Expected ListItemModel.Flavor"
        }
        tvFlavor.text = listItem.title
    }

}