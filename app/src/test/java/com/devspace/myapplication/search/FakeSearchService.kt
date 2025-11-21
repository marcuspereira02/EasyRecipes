package com.devspace.myapplication.search

import com.devspace.myapplication.search.data.SearchRecipesResponse
import com.devspace.myapplication.search.data.SearchService
import retrofit2.Response

class FakeSearchService : SearchService {

    var recipes : Response<SearchRecipesResponse>? = null
    var recipeByQueryException: Exception? = null

    override suspend fun searchRecipes(query: String): Response<SearchRecipesResponse> {
        recipeByQueryException?.let { throw it }

        return recipes
            ?: throw IllegalStateException("Response must be set in test")
    }
}