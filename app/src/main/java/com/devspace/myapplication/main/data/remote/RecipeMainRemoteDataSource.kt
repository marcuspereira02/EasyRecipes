package com.devspace.myapplication.main.data.remote

import android.accounts.NetworkErrorException
import com.devspace.myapplication.common.model.Recipe

class RecipeMainRemoteDataSource(private val mainService: MainService) {

    suspend fun getRandomRecipes(): Result<List<Recipe>?>{
        return try {
            val response = mainService.getRecipesRandom()
            if (response.isSuccessful){
                val recipes = response.body()?.recipes?.map {
                    Recipe(
                        id = it.id,
                        title = it.title,
                        image = it.image,
                        summary = it.summary
                    )
                }
                Result.success(recipes)
            } else {
                Result.failure(NetworkErrorException(response.message()))
            }
        }catch (ex: Exception){
            ex.printStackTrace()
            Result.failure(ex)
        }
    }
}