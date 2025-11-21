package com.devspace.myapplication.detail

import com.devspace.myapplication.common.data.remote.RecipeDto
import com.devspace.myapplication.detail.data.DetailService
import retrofit2.Response

class FakeDetailService : DetailService {

    var recipeByIdResponse: Response<RecipeDto>? = null
    var recipeByIdException: Exception? = null

    override suspend fun getRecipeInformation(id: String): Response<RecipeDto> {
        recipeByIdException?.let { throw it }

        return recipeByIdResponse
            ?: throw IllegalStateException("Response must be set in test")
    }

}