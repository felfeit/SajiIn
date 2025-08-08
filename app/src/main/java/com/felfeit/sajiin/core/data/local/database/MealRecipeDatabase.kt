package com.felfeit.sajiin.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.felfeit.sajiin.core.data.local.entities.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MealRecipeDatabase : RoomDatabase(){
    abstract val dao: MealRecipeDao
}