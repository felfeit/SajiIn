package com.felfeit.sajiin.core.data.remote.response


import com.google.gson.annotations.SerializedName

data class MealSummaryResponseItem(
    @SerializedName("idMeal")
    val idMeal: String? = null,
    @SerializedName("strMeal")
    val strMeal: String? = null ,
    @SerializedName("strMealThumb")
    val strMealThumb: String? = null
)