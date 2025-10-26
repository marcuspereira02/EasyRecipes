package com.devspace.myapplication.main.data.remote

import com.devspace.myapplication.common.model.RecipeResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainService {
    @GET("recipes/random?number=20")
    suspend fun getRecipesRandom() : Response<RecipeResponse>
}