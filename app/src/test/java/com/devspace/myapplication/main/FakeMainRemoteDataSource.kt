package com.devspace.myapplication.main

import com.devspace.myapplication.common.model.Recipe
import com.devspace.myapplication.main.data.remote.RemoteDataSource

class FakeMainRemoteDataSource : RemoteDataSource {

    var recipes : Result<List<Recipe>?> = Result.success(emptyList())

    override suspend fun getRandomRecipes(): Result<List<Recipe>?> {
        return recipes
    }
}