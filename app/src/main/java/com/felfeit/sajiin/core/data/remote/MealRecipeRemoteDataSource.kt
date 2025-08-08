package com.felfeit.sajiin.core.data.remote

import android.util.Log
import com.felfeit.sajiin.core.data.remote.network.MealRecipeApiService
import com.felfeit.sajiin.core.data.remote.network.ResponseStatus
import com.felfeit.sajiin.core.data.remote.response.MealDetailResponseItem
import com.felfeit.sajiin.core.data.remote.response.MealSummaryResponseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class MealRecipeRemoteDataSource(private val apiService: MealRecipeApiService) {
    fun getMealRecipes(letter: String): Flow<ResponseStatus<List<MealDetailResponseItem>>> {
        return flow {
            try {
                val response = apiService.getMealRecipesByFirstLetter(letter)
                val meals = response.meals
                if (!meals.isNullOrEmpty()) {
                    emit(ResponseStatus.Success(meals))
                } else {
                    emit(ResponseStatus.Empty)
                }
            } catch (e: Exception) {
                emit(ResponseStatus.Error(e.toString()))
                Log.d(TAG, "getMealRecipes: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun searchMealRecipes(name: String): Flow<ResponseStatus<List<MealDetailResponseItem>>> {
        return flow {
            try {
                val response = apiService.searchMealRecipesByName(name)
                val meals = response.meals
                if (!meals.isNullOrEmpty()) {
                    emit(ResponseStatus.Success(meals))
                } else {
                    emit(ResponseStatus.Empty)
                }
            } catch (e: Exception) {
                emit(ResponseStatus.Error(e.toString()))
                Log.d(TAG, "searchMealRecipes: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMealRecipesByArea(area: String): Flow<ResponseStatus<List<MealSummaryResponseItem>>> {
        return flow {
            try {
                val response = apiService.getMealRecipesByArea(area)
                val meals = response.meals
                if (!meals.isNullOrEmpty()) {
                    emit(ResponseStatus.Success(meals))
                } else {
                    emit(ResponseStatus.Empty)
                }
            } catch (e: Exception) {
                emit(ResponseStatus.Error(e.toString()))
                Log.d(TAG, "getMealRecipesByArea: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMealRecipesByCategory(category: String): Flow<ResponseStatus<List<MealSummaryResponseItem>>> {
        return flow {
            try {
                val response = apiService.getMealRecipesByCategory(category)
                val meals = response.meals
                if (!meals.isNullOrEmpty()) {
                    emit(ResponseStatus.Success(meals))
                } else {
                    emit(ResponseStatus.Empty)
                }
            } catch (e: Exception) {
                emit(ResponseStatus.Error(e.toString()))
                Log.d(TAG, "getMealRecipesByCategory: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getMealDetailRecipeById(mealId: String): Flow<ResponseStatus<MealDetailResponseItem>> {
        return flow {
            try {
                val response = apiService.getMealDetailRecipeById(mealId)
                val meal = response.meals?.first()
                if(meal != null) {
                    emit(ResponseStatus.Success(meal))
                } else {
                 emit(ResponseStatus.Error("Meal not found"))
                }
            } catch (e: Exception) {
                emit(ResponseStatus.Error(e.toString()))
                Log.d(TAG, "getMealDetailRecipeById: $e")
            }
        }.flowOn(Dispatchers.IO)
    }


    companion object {
        private const val TAG = "MealRecipeRemoteDataSource"
    }
}