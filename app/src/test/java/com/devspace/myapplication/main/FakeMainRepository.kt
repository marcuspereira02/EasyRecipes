package com.devspace.myapplication.main

import com.devspace.myapplication.common.model.Recipe
import com.devspace.myapplication.main.data.MainRepository

class FakeMainRepository: MainRepository {

    var recipes : Result<List<Recipe>> = Result.success(emptyList())

    override suspend fun getAllRecipes(): Result<List<Recipe>?> {
     return recipes
    }
}