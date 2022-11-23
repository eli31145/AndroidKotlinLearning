package com.example.exercise602_catagentsrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise602_catagentsrecyclerview.models.CatModel
import com.example.exercise602_catagentsrecyclerview.models.ListItemModel
import com.example.exercise602_catagentsrecyclerview.viewholder.CatViewHolder
import com.example.exercise602_catagentsrecyclerview.viewholder.ListItemViewHolder
import com.example.exercise602_catagentsrecyclerview.viewholder.TitleViewHolder
import java.lang.IllegalArgumentException

private const val VIEW_TYPE_TITLE = 0
private const val VIEW_TYPE_CAT = 1

class ListItemsAdapter(private val inflater: LayoutInflater, private val imageLoader: ImageLoader, private val onClickListener: OnClickListener):RecyclerView.Adapter<ListItemViewHolder>() {

    val swipeToDeleteCallback = SwipeToDeleteCallback()
    private val listData = mutableListOf<ListItemModel>()

    fun setData(listData: List<ListItemModel>){
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType){
        VIEW_TYPE_TITLE -> {
            val view = inflater.inflate(R.layout.item_title, parent, false)
            TitleViewHolder(view)
        }
        VIEW_TYPE_CAT -> {
            val view = inflater.inflate(R.layout.item_cat, parent, false)
            CatViewHolder(view, imageLoader, object:
                CatViewHolder.OnClickListener{
                override fun onClick(catModel: CatModel) = onClickListener.onItemClick(catModel)
            })
        }
        else -> throw IllegalArgumentException("Unknown View Type requested: $viewType")
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun getItemViewType(position: Int): Int {
        when (listData[position]) {
            is ListItemModel.Title -> return VIEW_TYPE_TITLE
            is ListItemModel.Cat -> return VIEW_TYPE_CAT
        }
    }

    //original
    /*override fun getItemViewType(position: Int): Int =
        when (listData[position]) {
            is ListItemModel.Title -> VIEW_TYPE_TITLE
            is ListItemModel.Cat -> VIEW_TYPE_CAT
        }*/

    fun removeItem(position: Int){
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    fun addItem(position: Int, listItem: ListItemModel){
        listData.add(position, listItem)
        notifyItemInserted(position)
    }

    interface OnClickListener{
        fun onItemClick(catModel: CatModel)
    }

    inner class SwipeToDeleteCallback: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = if (viewHolder is CatViewHolder){
            makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) or
                    makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)
        } else 0

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }

    }


}