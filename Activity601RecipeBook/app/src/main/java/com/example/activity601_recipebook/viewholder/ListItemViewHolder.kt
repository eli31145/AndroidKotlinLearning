package com.example.activity601_recipebook.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.activity601_recipebook.models.ListItemModel

abstract class ListItemViewHolder(containerView: View):RecyclerView.ViewHolder(containerView) {

    abstract fun bind(listItem:ListItemModel)
}