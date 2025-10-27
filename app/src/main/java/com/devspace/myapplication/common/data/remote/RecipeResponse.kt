package com.devspace.myapplication.common.data.remote

data class RecipeResponse(
    val recipes: List<RecipeDto>
)

data class RecipeDto(
    val id : Int,
    val title: String,
    val image: String,
    val summary: String
)
