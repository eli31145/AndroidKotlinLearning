package com.example.activity601_recipebook.models

interface ListItemModel{
    data class Flavor(val title: String): ListItemModel
    //data class Recipe(val recipe: RecipeModel): ListItemModel

    data class RecipeModel(
        val title: String,
        val description: String,
        val flavor: FlavorTypes
    ): ListItemModel

    enum class FlavorTypes {
        SAVORY,
        SWEET
    }
}
