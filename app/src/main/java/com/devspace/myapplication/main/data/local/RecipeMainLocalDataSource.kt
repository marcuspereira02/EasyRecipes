package com.devspace.myapplication.main.data.local

import com.devspace.myapplication.common.data.local.RecipeDao
import com.devspace.myapplication.common.data.local.RecipeEntity
import com.devspace.myapplication.common.model.Recipe

class RecipeMainLocalDataSource(private val dao: RecipeDao) {

    suspend fun getRecipesRandom() : List<Recipe>{
        val entities = dao.getAllRecipes()

        return  entities.map {
            Recipe(
                id = it.id,
                title = it.title,
                image = it.image,
                summary = it.summary
            )
        }
    }

    suspend fun updateLocalItems(recipes: List<Recipe>){
        val entities = recipes.map {
            RecipeEntity(
            id = it.id,
            title = it.title,
            image = it.image,
            summary = it.summary
            )
        }
        dao.insertAll(entities)
    }
}