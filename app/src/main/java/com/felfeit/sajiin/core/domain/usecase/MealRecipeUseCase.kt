package com.felfeit.sajiin.core.domain.usecase

import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.models.MealModel
import kotlinx.coroutines.flow.Flow

interface MealRecipeUseCase {
    fun getMeals(letter: String = "a"): Flow<Resource<List<MealDetailModel>>>
    fun searchMealsByName(name: String): Flow<Resource<List<MealDetailModel>>>
    fun getMealsByArea(area: String = "American"): Flow<Resource<List<MealModel>>>
    fun getMealsByCategory(category: String): Flow<Resource<List<MealModel>>>
    fun getMealDetail(id: String): Flow<Resource<MealDetailModel>>
    fun getFavoriteMeals(): Flow<List<MealModel>>
    suspend fun insertFavoriteMeal(meal: MealDetailModel)
    suspend fun deleteFavoriteMeal(meal: MealModel)
    suspend fun getFavoriteMealById(id: String): MealModel?
}