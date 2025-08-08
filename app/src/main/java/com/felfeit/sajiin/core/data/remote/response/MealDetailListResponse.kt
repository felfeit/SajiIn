package com.felfeit.sajiin.core.data.remote.response


import com.google.gson.annotations.SerializedName

data class MealDetailListResponse(
    @SerializedName("meals")
    val meals: List<MealDetailResponseItem>? = emptyList()
)