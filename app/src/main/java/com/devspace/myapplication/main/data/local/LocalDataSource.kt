package com.devspace.myapplication.main.data.local

import com.devspace.myapplication.common.model.Recipe

interface LocalDataSource {
    suspend fun getRecipesRandom(): List<Recipe>
    suspend fun updateLocalItems(recipes: List<Recipe>)
}