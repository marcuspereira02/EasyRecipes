package com.devspace.myapplication.detail.presentation.ui

data class RecipeDetailUiState(
    val data : RecipeDetailUiData? = null,
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String? = "Something went wrong"
)

data class RecipeDetailUiData(
    val id : Int,
    val title: String,
    val image: String,
    val summary: String
)