package com.felfeit.sajiin.core.data.remote.response


import com.google.gson.annotations.SerializedName

data class MealSummaryListResponse(
    @SerializedName("meals")
    val meals: List<MealSummaryResponseItem>? = emptyList()
)