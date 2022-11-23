package com.example.exercise602_catagentsrecyclerview.viewholder

import android.view.View
import android.widget.TextView
import com.example.exercise602_catagentsrecyclerview.R
import com.example.exercise602_catagentsrecyclerview.models.ListItemModel

class TitleViewHolder(containerView: View): ListItemViewHolder(containerView) {

    private val titleView: TextView by lazy { containerView.findViewById(R.id.tv_title_title) }

    override fun bind(listItem: ListItemModel) {
        require(listItem is ListItemModel.Title) {
            "Expected ListItemModel.Title"
        }
        titleView.text = listItem.title
    }

}