package com.felfeit.sajiin.meals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.felfeit.sajiin.core.domain.usecase.MealRecipeUseCase

class MealViewModel(private val useCase: MealRecipeUseCase) : ViewModel() {
    fun getMealRecipesByCategory(category: String) =
        useCase.getMealsByCategory(category).asLiveData()
}