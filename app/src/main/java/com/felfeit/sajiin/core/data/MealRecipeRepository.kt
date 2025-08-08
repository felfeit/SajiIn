package com.felfeit.sajiin.core.data

import android.util.Log
import com.felfeit.sajiin.core.data.local.MealRecipeLocalDataSource
import com.felfeit.sajiin.core.data.remote.MealRecipeRemoteDataSource
import com.felfeit.sajiin.core.data.remote.network.ResponseStatus
import com.felfeit.sajiin.core.domain.models.MealDetailModel
import com.felfeit.sajiin.core.domain.models.MealModel
import com.felfeit.sajiin.core.domain.repositories.IMealRecipeRepository
import com.felfeit.sajiin.core.utils.toDomain
import com.felfeit.sajiin.core.utils.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MealRecipeRepository(
    private val remoteDataSource: MealRecipeRemoteDataSource,
    private val localDataSource: MealRecipeLocalDataSource,
) : IMealRecipeRepository {
    override fun getMealRecipes(letter: String): Flow<Resource<List<MealDetailModel>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getMealRecipes(letter).collect { response ->
                when (response) {
                    is ResponseStatus.Success -> {
                        val meals = response.data.map { it.toDomain() }
                        Log.d(TAG, meals.joinToString(", "))
                        emit(Resource.Success(meals))
                    }

                    is ResponseStatus.Empty -> emit(Resource.Error("No meals found"))
                    is ResponseStatus.Error -> emit(Resource.Error(response.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun searchMealRecipesByName(name: String): Flow<Resource<List<MealDetailModel>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.searchMealRecipes(name).collect { response ->
                when (response) {
                    is ResponseStatus.Success -> {
                        val meals = response.data.map { it.toDomain() }
                        emit(Resource.Success(meals))
                    }

                    is ResponseStatus.Empty -> emit(Resource.Error("No meals found"))
                    is ResponseStatus.Error -> emit(Resource.Error(response.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMealRecipesByArea(area: String): Flow<Resource<List<MealModel>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getMealRecipesByArea(area).collect { response ->
                when (response) {
                    is ResponseStatus.Success -> {
                        val meals = response.data.map { it.toDomain() }
                        emit(Resource.Success(meals))
                    }

                    is ResponseStatus.Empty -> emit(Resource.Error("No meals found"))
                    is ResponseStatus.Error -> emit(Resource.Error(response.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMealRecipesByCategory(category: String): Flow<Resource<List<MealModel>>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getMealRecipesByCategory(category).collect { response ->
                when (response) {
                    is ResponseStatus.Success -> {
                        val meals = response.data.map { it.toDomain() }
                        emit(Resource.Success(meals))
                    }

                    is ResponseStatus.Empty -> emit(Resource.Error("No meals found"))
                    is ResponseStatus.Error -> emit(Resource.Error(response.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getMealDetailRecipe(id: String): Flow<Resource<MealDetailModel>> {
        return flow {
            emit(Resource.Loading())
            remoteDataSource.getMealDetailRecipeById(id).collect { response ->
                when(response){
                    is ResponseStatus.Success -> {
                        val meal = response.data.toDomain()
                        emit(Resource.Success(meal))
                    }
                    is ResponseStatus.Empty -> emit(Resource.Error("No meals found"))
                    is ResponseStatus.Error -> emit(Resource.Error(response.message))
                }
            }
        }.flowOn(Dispatchers.IO)
    }

    override fun getFavoriteMealRecipes(): Flow<List<MealModel>> {
        return localDataSource.getFavoriteMealRecipes().map { mealEntities ->
            mealEntities.map { it.toDomain() }
        }
    }

    override suspend fun insertFavoriteMealRecipe(meal: MealDetailModel) {
        localDataSource.insertFavoriteMealRecipe(meal)
    }

    override suspend fun deleteFavoriteMealRecipe(meal: MealModel) {
        localDataSource.deleteFavoriteMealRecipe(meal.toEntity())
    }

    override suspend fun getFavoriteMealRecipeById(id: String): MealModel? {
        return localDataSource.getFavoriteMealRecipeById(id)?.toDomain()
    }

    companion object {
        private const val TAG = "MealRecipeRepository"
    }
}