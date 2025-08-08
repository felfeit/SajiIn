package com.felfeit.sajiin.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.usecase.MealRecipeUseCase
import com.felfeit.sajiin.core.utils.toMealModel
import kotlinx.coroutines.launch

class BookmarkViewModel(private val useCase: MealRecipeUseCase) : ViewModel() {
    val recipes = useCase.getFavoriteMeals().asLiveData()

    fun deleteRecipe(meal: MealDetailModel) {
        viewModelScope.launch {
            useCase.deleteFavoriteMeal(meal.toMealModel())
        }
    }
}