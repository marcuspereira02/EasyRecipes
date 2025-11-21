package com.devspace.myapplication.main.data

import com.devspace.myapplication.common.model.Recipe

interface MainRepository  {
    suspend fun getAllRecipes(): Result<List<Recipe>?>
}