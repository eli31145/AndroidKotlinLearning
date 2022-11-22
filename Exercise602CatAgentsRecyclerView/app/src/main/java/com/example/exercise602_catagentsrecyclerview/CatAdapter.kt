package com.example.exercise602_catagentsrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise602_catagentsrecyclerview.models.CatModel

class CatAdapter(private val inflater: LayoutInflater, private val imageLoader: ImageLoader):RecyclerView.Adapter<CatViewHolder>() {

    private val catList = mutableListOf<CatModel>()

    fun setData(catList: List<CatModel>){
        this.catList.clear()
        this.catList.addAll(catList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val view = inflater.inflate(R.layout.item_cat, parent, false)
        return CatViewHolder(view, imageLoader)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        holder.bind(catList[position])
    }

    override fun getItemCount(): Int {
        return catList.size
    }
}