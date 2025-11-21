package com.devspace.myapplication.main.data

import com.devspace.myapplication.common.model.Recipe
import com.devspace.myapplication.main.data.local.LocalDataSource
import com.devspace.myapplication.main.data.remote.RemoteDataSource

class RecipeMainRepository(
    private val local: LocalDataSource,
    private val remote: RemoteDataSource
) : MainRepository {

    override suspend fun getAllRecipes(): Result<List<Recipe>?> {
        return try {
            val result = remote.getRandomRecipes()
            if (result.isSuccess) {
                val recipesRemote = result.getOrNull() ?: emptyList()
                if (recipesRemote.isNotEmpty()) {
                    local.updateLocalItems(recipesRemote)
                }
                Result.success(local.getRecipesRandom())
            } else {
                val localData = local.getRecipesRandom()
                if (localData.isEmpty()) {
                    return result
                } else {
                    Result.success(localData)
                }
            }
        } catch (ex: Exception) {
            Result.failure(ex)
        }
    }
}