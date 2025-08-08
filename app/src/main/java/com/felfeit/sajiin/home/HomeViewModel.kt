package com.felfeit.sajiin.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.felfeit.sajiin.core.domain.usecase.MealRecipeUseCase

class HomeViewModel(private val useCase: MealRecipeUseCase) : ViewModel() {
    fun getRecommendedMealRecipes(letter: String) = useCase.getMeals(letter).   asLiveData()
    fun getPopularMealRecipes(letter: String) = useCase.getMeals(letter).asLiveData()
}