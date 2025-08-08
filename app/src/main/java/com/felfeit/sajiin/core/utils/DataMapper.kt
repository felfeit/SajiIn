package com.felfeit.sajiin.core.utils

import com.felfeit.sajiin.core.data.local.entities.MealEntity
import com.felfeit.sajiin.core.data.remote.response.MealDetailResponseItem
import com.felfeit.sajiin.core.data.remote.response.MealSummaryResponseItem
import com.felfeit.sajiin.core.domain.models.Ingredient
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.models.MealModel

fun MealDetailResponseItem.toDomain(): MealDetailModel {
    val ingredients = mutableListOf<Ingredient>().apply {
        if (!strIngredient1.isNullOrBlank()) add(Ingredient(strIngredient1, strMeasure1 ?: ""))
        if (!strIngredient2.isNullOrBlank()) add(Ingredient(strIngredient2, strMeasure2 ?: ""))
        if (!strIngredient3.isNullOrBlank()) add(Ingredient(strIngredient3, strMeasure3 ?: ""))
        if (!strIngredient4.isNullOrBlank()) add(Ingredient(strIngredient4, strMeasure4 ?: ""))
        if (!strIngredient5.isNullOrBlank()) add(Ingredient(strIngredient5, strMeasure5 ?: ""))
        if (!strIngredient6.isNullOrBlank()) add(Ingredient(strIngredient6, strMeasure6 ?: ""))
        if (!strIngredient7.isNullOrBlank()) add(Ingredient(strIngredient7, strMeasure7 ?: ""))
        if (!strIngredient8.isNullOrBlank()) add(Ingredient(strIngredient8, strMeasure8 ?: ""))
        if (!strIngredient9.isNullOrBlank()) add(Ingredient(strIngredient9, strMeasure9 ?: ""))
        if (!strIngredient10.isNullOrBlank()) add(Ingredient(strIngredient10, strMeasure10 ?: ""))
        if (!strIngredient11.isNullOrBlank()) add(Ingredient(strIngredient11, strMeasure11 ?: ""))
        if (!strIngredient12.isNullOrBlank()) add(Ingredient(strIngredient12, strMeasure12 ?: ""))
        if (!strIngredient13.isNullOrBlank()) add(Ingredient(strIngredient13, strMeasure13 ?: ""))
        if (!strIngredient14.isNullOrBlank()) add(Ingredient(strIngredient14, strMeasure14 ?: ""))
        if (!strIngredient15.isNullOrBlank()) add(Ingredient(strIngredient15, strMeasure15 ?: ""))
        if (!strIngredient16.isNullOrBlank()) add(Ingredient(strIngredient16, strMeasure16 ?: ""))
        if (!strIngredient17.isNullOrBlank()) add(Ingredient(strIngredient17, strMeasure17 ?: ""))
        if (!strIngredient18.isNullOrBlank()) add(Ingredient(strIngredient18, strMeasure18 ?: ""))
        if (!strIngredient19.isNullOrBlank()) add(Ingredient(strIngredient19, strMeasure19 ?: ""))
        if (!strIngredient20.isNullOrBlank()) add(Ingredient(strIngredient20, strMeasure20 ?: ""))
    }

    return MealDetailModel(
        id = this.idMeal ?: "",
        name = this.strMeal ?: "",
        category = this.strCategory ?: "",
        area = this.strArea ?: "",
        instructions = this.strInstructions ?: "",
        thumbnail = this.strMealThumb ?: "",
        youtubeLink = this.strYoutube ?: "",
        ingredients = ingredients
    )
}

fun MealDetailModel.toEntity(): MealEntity {
    return MealEntity(
        mealId = id,
        mealName = name,
        mealImage = thumbnail,
        mealCategory = category,
        mealArea = area
    )
}

fun MealSummaryResponseItem.toDomain(): MealModel {
    return MealModel(
        id = idMeal ?: "",
        name = strMeal ?: "",
        image = strMealThumb ?: "",
        category = "",
        area = ""
    )
}

fun MealEntity.toDomain(): MealModel {
    return MealModel(
        id = mealId,
        name = mealName,
        image = mealImage,
        category = mealCategory,
        area = mealArea
    )
}

fun MealModel.toEntity(): MealEntity {
    return MealEntity(
        mealId = id,
        mealName = name,
        mealImage = image,
        mealCategory = category,
        mealArea = area
    )
}

fun MealDetailModel.toMealModel(): MealModel {
    return MealModel(
        id = id,
        name = name,
        image = thumbnail,
        category = category,
        area = area
    )
}