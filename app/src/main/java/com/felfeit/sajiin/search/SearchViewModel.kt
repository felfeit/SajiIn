package com.felfeit.sajiin.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.felfeit.sajiin.core.domain.usecase.MealRecipeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest

class SearchViewModel(private val useCase: MealRecipeUseCase) : ViewModel() {
    val searchQuery = MutableStateFlow("")

    val recipes = searchQuery
        .debounce(750L)
        .filter { query ->
            query.trim().isNotEmpty()
        }
        .distinctUntilChanged()
        .flatMapLatest { query ->
            useCase.searchMealsByName(query)
        }
        .asLiveData()

    fun setSearchQuery(query: String) {
        searchQuery.value = query
    }
}