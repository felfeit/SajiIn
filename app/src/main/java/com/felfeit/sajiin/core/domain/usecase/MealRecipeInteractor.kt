package com.felfeit.sajiin.core.domain.usecase

import com.felfeit.sajiin.core.data.Resource
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.models.MealModel
import com.felfeit.sajiin.core.domain.repositories.IMealRecipeRepository
import kotlinx.coroutines.flow.Flow

class MealRecipeInteractor(private val repository: IMealRecipeRepository): MealRecipeUseCase {
    override fun getMeals(letter: String): Flow<Resource<List<MealDetailModel>>> {
        return repository.getMealRecipes(letter)
    }

    override fun searchMealsByName(name: String): Flow<Resource<List<MealDetailModel>>> {
        return repository.searchMealRecipesByName(name)
    }

    override fun getMealsByArea(area: String): Flow<Resource<List<MealModel>>> {
        return repository.getMealRecipesByArea(area)
    }

    override fun getMealsByCategory(category: String): Flow<Resource<List<MealModel>>> {
        return repository.getMealRecipesByCategory(category)
    }

    override fun getMealDetail(id: String): Flow<Resource<MealDetailModel>> {
        return repository.getMealDetailRecipe(id)
    }

    override fun getFavoriteMeals(): Flow<List<MealModel>> {
        return repository.getFavoriteMealRecipes()
    }

    override suspend fun insertFavoriteMeal(meal: MealDetailModel) {
        return repository.insertFavoriteMealRecipe(meal)
    }

    override suspend fun deleteFavoriteMeal(meal: MealModel) {
        return repository.deleteFavoriteMealRecipe(meal)
    }

    override suspend fun getFavoriteMealById(id: String): MealModel? {
        return repository.getFavoriteMealRecipeById(id)
    }
}