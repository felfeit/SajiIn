package com.felfeit.sajiin.core.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealEntity(
    @PrimaryKey
    val mealId: String,
    val mealName: String,
    val mealImage: String,
    val mealCategory: String,
    val mealArea: String,
)