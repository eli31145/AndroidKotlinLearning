package com.example.activity601_recipebook

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.activity601_recipebook.models.ListItemModel
import com.example.activity601_recipebook.viewholder.FlavorViewHolder
import com.example.activity601_recipebook.viewholder.ListItemViewHolder
import com.example.activity601_recipebook.viewholder.RecipeViewHolder
import java.lang.IllegalArgumentException
import java.lang.IllegalStateException

const val VIEW_TYPE_FLAVOR = 0
const val VIEW_TYPE_RECIPE = 1

class ListItemAdapter(private val layoutInflater: LayoutInflater, private val onClickListener: OnClickListener): RecyclerView.Adapter<ListItemViewHolder>() {

    private val savoryTitle = ListItemModel.Flavor("Savory")
    private val sweetTitle = ListItemModel.Flavor("Sweet")
    private val listData = mutableListOf<ListItemModel>(savoryTitle, sweetTitle)
    val swipeToDelete = SwipeToDelete()

    override fun getItemViewType(position: Int): Int = when (listData[position]){
        is ListItemModel.Flavor -> VIEW_TYPE_FLAVOR
        is ListItemModel.RecipeModel -> VIEW_TYPE_RECIPE
        else -> throw IllegalStateException("Unexpected data happening at $position")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder =
        when (viewType){
            VIEW_TYPE_FLAVOR -> {
                val view = layoutInflater.inflate(R.layout.item_flavor, parent, false)
                FlavorViewHolder(view)
            }
            VIEW_TYPE_RECIPE -> {
                val view = layoutInflater.inflate(R.layout.item_recipe, parent, false)
                RecipeViewHolder(view, object: RecipeViewHolder.OnClickListener{
                    override fun onClick(recipe: ListItemModel.RecipeModel) = onClickListener.onSelect(recipe)
                })
            } else -> throw IllegalArgumentException("Unknown View Type requested: $viewType")
        }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        holder.bind(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    //create function for addItem
    fun addItem(recipe: ListItemModel.RecipeModel){
        val insertTo = listData.indexOf( when(recipe.flavor){
            ListItemModel.FlavorTypes.SAVORY -> savoryTitle
            ListItemModel.FlavorTypes.SWEET -> sweetTitle
        }) + 1
        listData.add(insertTo, recipe)
        notifyItemInserted(insertTo)
    }

    //create function for deleteItem
    fun deleteItem(position: Int){
        listData.removeAt(position)
        notifyItemRemoved(position)
    }

    interface OnClickListener{
        fun onSelect(recipe: ListItemModel.RecipeModel)
    }

    inner class SwipeToDelete:ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            deleteItem(position)
        }

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int = if (viewHolder is RecipeViewHolder){
            makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) or
                    makeMovementFlags(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.LEFT or ItemTouchHelper.LEFT)
        } else 0

    }

}