package com.devspace.myapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("recipes/random?number=20")
    fun getRecipesRandom() : Call<RecipeResponse>

    @GET("recipes/{id}/information?includeNutrition=false")
    fun getRecipeInformation(@Path("id") id: String): Call<RecipeDto>
}