package com.felfeit.sajiin.core.domain.repositories

import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.models.MealModel
import kotlinx.coroutines.flow.Flow

interface IMealRecipeRepository {
    fun getMealRecipes(letter: String): Flow<Resource<List<MealDetailModel>>>
    fun searchMealRecipesByName(name: String): Flow<Resource<List<MealDetailModel>>>
    fun getMealRecipesByArea(area: String): Flow<Resource<List<MealModel>>>
    fun getMealRecipesByCategory(category: String): Flow<Resource<List<MealModel>>>
    fun getMealDetailRecipe(id: String): Flow<Resource<MealDetailModel>>
    fun getFavoriteMealRecipes(): Flow<List<MealModel>>
    suspend fun insertFavoriteMealRecipe(meal: MealDetailModel)
    suspend fun deleteFavoriteMealRecipe(meal: MealModel)
    suspend fun getFavoriteMealRecipeById(id: String): MealModel?
}