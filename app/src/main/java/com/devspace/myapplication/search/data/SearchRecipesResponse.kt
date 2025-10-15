package com.devspace.myapplication.search.data

data class SearchRecipesResponse(
    val results: List<SearchRecipeDto>
)

data class SearchRecipeDto(
    val id: Int,
    val title: String,
    val image: String
)
