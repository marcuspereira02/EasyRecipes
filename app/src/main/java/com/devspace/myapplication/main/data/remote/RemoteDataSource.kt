package com.devspace.myapplication.main.data.remote
import com.devspace.myapplication.common.model.Recipe

interface RemoteDataSource {

    suspend fun getRandomRecipes(): Result<List<Recipe>?>
}