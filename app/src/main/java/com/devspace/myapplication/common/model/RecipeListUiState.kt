package com.devspace.myapplication.common.model

data class RecipeListUiState(
    val list : List<RecipeListUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)

data class RecipeListUiData(
    val id : Int,
    val title: String,
    val image: String,
    val summary: String
)