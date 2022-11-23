package com.example.exercise602_catagentsrecyclerview.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise602_catagentsrecyclerview.models.ListItemModel

abstract class ListItemViewHolder(containerView: View):RecyclerView.ViewHolder(containerView) {
    abstract fun bind(listItem: ListItemModel)
}