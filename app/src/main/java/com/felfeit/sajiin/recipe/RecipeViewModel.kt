package com.felfeit.sajiin.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.usecase.MealRecipeUseCase
import com.felfeit.sajiin.core.utils.toMealModel
import kotlinx.coroutines.launch

class RecipeViewModel(private val useCase: MealRecipeUseCase) : ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getMealRecipe(id: String) = useCase.getMealDetail(id).asLiveData()

    fun toggleFavorite(meal: MealDetailModel) {
        viewModelScope.launch {
            val currentStatus = _isFavorite.value ?: false
            if (currentStatus) {
                useCase.deleteFavoriteMeal(meal.toMealModel())
            } else {
                useCase.insertFavoriteMeal(meal)
            }
            _isFavorite.value = !currentStatus
        }
    }

    fun checkFavoriteStatus(mealId: String) {
        viewModelScope.launch {
            _isFavorite.value = useCase.getFavoriteMealById(mealId) != null
        }
    }
}