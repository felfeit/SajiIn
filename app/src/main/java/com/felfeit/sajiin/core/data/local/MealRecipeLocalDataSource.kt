package com.felfeit.sajiin.core.data.local

import com.felfeit.sajiin.core.data.local.database.MealRecipeDao
import com.felfeit.sajiin.core.data.local.entities.MealEntity
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.utils.toEntity
import kotlinx.coroutines.flow.Flow

class MealRecipeLocalDataSource(private val dao: MealRecipeDao) {
    suspend fun insertFavoriteMealRecipe(meal: MealDetailModel) {
        return dao.insertFavoriteMealRecipe(meal.toEntity())
    }

    suspend fun deleteFavoriteMealRecipe(entity: MealEntity) {
        return dao.deleteFavoriteMealRecipe(entity)
    }

    fun getFavoriteMealRecipes(): Flow<List<MealEntity>> {
        return dao.getFavoriteMealRecipes()
    }

    suspend fun getFavoriteMealRecipeById(mealId: String): MealEntity? {
        return dao.getFavoriteMealRecipeById(mealId)
    }
}