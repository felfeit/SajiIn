package com.felfeit.sajiin.core.di

import androidx.room.Room
import com.felfeit.sajiin.core.data.MealRecipeRepository
import com.felfeit.sajiin.core.data.local.MealRecipeLocalDataSource
import com.felfeit.sajiin.core.data.local.database.MealRecipeDatabase
import com.felfeit.sajiin.core.data.remote.MealRecipeRemoteDataSource
import com.felfeit.sajiin.core.data.remote.network.MealRecipeApiService
import com.felfeit.sajiin.core.domain.repositories.IMealRecipeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    factory {
        get<MealRecipeDatabase>().dao
    }
    single {
        Room.databaseBuilder(
            androidContext(),
            MealRecipeDatabase::class.java,
            "meal_recipes.db"
        ).fallbackToDestructiveMigration(false).build()
    }
}

val databaseModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()

        retrofit.create(MealRecipeApiService::class.java)
    }
}

val repositoryModule = module {
    single { MealRecipeLocalDataSource(get()) }
    single { MealRecipeRemoteDataSource(get()) }
    single<IMealRecipeRepository> { MealRecipeRepository(get(), get()) }
}