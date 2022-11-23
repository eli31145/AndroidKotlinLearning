package com.example.exercise602_catagentsrecyclerview.models

sealed class ListItemModel {
    data class Title(val title: String): ListItemModel()
    data class Cat(val catModel: CatModel): ListItemModel()
}