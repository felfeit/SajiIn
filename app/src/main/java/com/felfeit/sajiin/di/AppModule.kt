package com.felfeit.sajiin.di

import com.felfeit.sajiin.core.domain.usecase.MealRecipeInteractor
import com.felfeit.sajiin.core.domain.usecase.MealRecipeUseCase
import com.felfeit.sajiin.bookmark.BookmarkViewModel
import com.felfeit.sajiin.home.HomeViewModel
import com.felfeit.sajiin.meals.MealViewModel
import com.felfeit.sajiin.recipe.RecipeViewModel
import com.felfeit.sajiin.search.SearchViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<MealRecipeUseCase> { MealRecipeInteractor(get()) }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { BookmarkViewModel(get()) }
    viewModel { RecipeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { MealViewModel(get()) }
}