package com.devspace.myapplication.main

import com.devspace.myapplication.common.model.Recipe
import com.devspace.myapplication.main.data.local.LocalDataSource

class FakeMainLocalDataSource : LocalDataSource {

    var recipes : List<Recipe> = emptyList()
    var update : List<Recipe> = emptyList()

    override suspend fun getRecipesRandom(): List<Recipe> {
        return recipes
    }

    override suspend fun updateLocalItems(recipes: List<Recipe>) {
        update = recipes
    }
}