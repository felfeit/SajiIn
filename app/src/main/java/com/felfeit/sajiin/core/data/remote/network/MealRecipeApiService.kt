package com.felfeit.sajiin.core.data.remote.network

import com.felfeit.sajiin.core.data.remote.response.MealDetailListResponse
import com.felfeit.sajiin.core.data.remote.response.MealSummaryListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MealRecipeApiService {
    @GET("search.php")
    suspend fun getMealRecipesByFirstLetter(
        @Query("f") letter: String,
    ): MealDetailListResponse

    @GET("search.php")
    suspend fun searchMealRecipesByName(
        @Query("s") name: String,
    ): MealDetailListResponse

    @GET("filter.php")
    suspend fun getMealRecipesByArea(
        @Query("a") area: String,
    ): MealSummaryListResponse

    @GET("filter.php")
    suspend fun getMealRecipesByCategory(
        @Query("c") category: String,
    ): MealSummaryListResponse

    @GET("lookup.php")
    suspend fun getMealDetailRecipeById(
        @Query("i") id: String,
    ): MealDetailListResponse
}