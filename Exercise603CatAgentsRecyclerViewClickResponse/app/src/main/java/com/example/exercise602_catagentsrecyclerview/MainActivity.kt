package com.example.exercise602_catagentsrecyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise602_catagentsrecyclerview.models.CatBreed
import com.example.exercise602_catagentsrecyclerview.models.CatModel
import com.example.exercise602_catagentsrecyclerview.models.Gender
import com.example.exercise602_catagentsrecyclerview.models.ListItemModel

class MainActivity : AppCompatActivity() {

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.recycler_view) }
    private val listItemsAdapter: ListItemsAdapter by lazy { ListItemsAdapter(layoutInflater, GlideImageLoader(this), object: ListItemsAdapter.OnClickListener{
        override fun onItemClick(catModel: CatModel) = showSelectionDialog(catModel)
    }) }
    private val addItemButton: View by lazy { findViewById(R.id.btn_add_item) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.adapter = listItemsAdapter
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)

        val itemTouchHelper = ItemTouchHelper(listItemsAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        addItemButton.setOnClickListener {
            listItemsAdapter.addItem(
                1,
                ListItemModel.Cat(
                    CatModel(
                        Gender.Female,
                        CatBreed.BalineseJavanese,
                        "Anonymous",
                        "Unknown",
                        "https://cdn2.thecatapi.com/images/zJkeHza2K.jpg"
                    ) )
            )
        }

        listItemsAdapter.setData(
            listOf(
                ListItemModel.Title("Sleeper Agents"),
                ListItemModel.Cat(
                    CatModel(
                        Gender.Male,
                        CatBreed.ExoticShorthair,
                        "Garvey",
                        "Garvey is as a lazy, fat, and cynical orange cat.",
                        "https://cdn2.thecatapi.com/images/FZpeiLi4n.jpg"
                    )
                ),
                ListItemModel.Cat(
                    CatModel(
                        Gender.Unknown,
                        CatBreed.AmericanCurl,
                        "Curious George",
                        "Award winning investigator",
                        "https://cdn2.thecatapi.com/images/vJB8rwfdX.jpg"
                    )
                ),
                ListItemModel.Title("Active Agents"),
                ListItemModel.Cat(
                    CatModel(
                        Gender.Male,
                        CatBreed.BalineseJavanese,
                        "Fred",
                        "Silent and deadly",
                        "https://cdn2.thecatapi.com/images/DBmIBhhyv.jpg"
                    )
                ),
                ListItemModel.Cat(
                    CatModel(
                        Gender.Female,
                        CatBreed.ExoticShorthair,
                        "Wilma",
                        "Cuddly assassin",
                        "https://cdn2.thecatapi.com/images/KJF8fB_20.jpg"
                    )
                ),
                ListItemModel.Cat(
                    CatModel(
                        Gender.Male,
                        CatBreed.ExoticShorthair,
                        "Tim",
                        "Tim, AKA Jasper, is very energetic, determined yet somewhat... Slow.",
                        "https://cdn2.thecatapi.com/images/y61B6bFCh.jpg"
                    )
                )
            )
        )
    }
    private fun showSelectionDialog(catModel: CatModel){
            AlertDialog.Builder(this)
                .setTitle("Agent Selected")
                .setMessage("You have selected agent ${catModel.name}")
                .setPositiveButton("OK") {_ , _ ->}
                .show()
    }

}