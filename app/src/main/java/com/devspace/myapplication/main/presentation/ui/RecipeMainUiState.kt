package com.devspace.myapplication.main.presentation.ui

data class RecipeMainUiState(
    val list : List<RecipeUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)

data class RecipeUiData(
    val id : Int,
    val title: String,
    val image: String,
    val summary: String
)