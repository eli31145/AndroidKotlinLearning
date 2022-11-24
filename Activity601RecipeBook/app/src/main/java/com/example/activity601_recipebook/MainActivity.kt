package com.example.activity601_recipebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.activity601_recipebook.models.ListItemModel


class MainActivity : AppCompatActivity() {

    private val recyclerView:RecyclerView by lazy { findViewById(R.id.recycler_view) }
    private val btnAddSavory:View by lazy { findViewById(R.id.btn_add_savory) }
    private val btnAddSweet:View by lazy { findViewById(R.id.btn_add_sweet) }
    private val etTitle:TextView by lazy { findViewById(R.id.et_recipe_title) }
    private val etDesc:TextView by lazy { findViewById(R.id.et_recipe_desc) }

    private val listItemAdapter: ListItemAdapter by lazy { ListItemAdapter(layoutInflater, object: ListItemAdapter.OnClickListener{
        override fun onSelect(recipe: ListItemModel.RecipeModel) = showAlertDialog(recipe)
    }) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = listItemAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val itemTouchHelper = ItemTouchHelper(listItemAdapter.swipeToDelete)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //after setup finish, add onclick method
        btnAddSavory.setOnClickListener {
            addRecipeAndClearForm(ListItemModel.FlavorTypes.SAVORY)
        }
        btnAddSweet.setOnClickListener {
            addRecipeAndClearForm(ListItemModel.FlavorTypes.SWEET)
        }
    }

    private fun showAlertDialog(recipe: ListItemModel.RecipeModel){
        AlertDialog.Builder(this)
            .setTitle(recipe.title)
            .setMessage(recipe.description)
            .setPositiveButton("OK")  {_, _ ->}
            .show()
    }

    private fun addRecipeAndClearForm(flavor: ListItemModel.FlavorTypes){
        val title = etTitle.text.toString().trim()
        val description = etDesc.text.toString().trim()
        if (title.isBlank() || description.isBlank()){
            return
        }
        listItemAdapter.addItem(
            ListItemModel.RecipeModel(
                title,
                description,
                flavor
            )
        )

        etTitle.text = ""
        etDesc.text = ""
    }

}