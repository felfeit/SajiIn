package com.felfeit.sajiin.core.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felfeit.sajiin.core.data.local.entities.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealRecipeDao {
    @Query("SELECT * FROM meal")
    fun getFavoriteMealRecipes(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meal WHERE mealId = :mealId")
    suspend fun getFavoriteMealRecipeById(mealId: String): MealEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteMealRecipe(entity: MealEntity)

    @Delete
    suspend fun deleteFavoriteMealRecipe(entity: MealEntity)
}