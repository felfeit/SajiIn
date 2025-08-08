package com.felfeit.sajiin.core.domain.models

data class MealDetailModel(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: String,
    val thumbnail: String,
    val youtubeLink: String,
    val ingredients: List<Ingredient>
)