package com.devspace.myapplication.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.EasyRecipesApplication
import com.devspace.myapplication.main.data.RecipeMainRepository
import com.devspace.myapplication.common.model.RecipeListUiState
import com.devspace.myapplication.common.model.RecipeListUiData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class MainViewModel(
    private val repository: RecipeMainRepository
) : ViewModel() {

    private val _uiRecipes = MutableStateFlow(RecipeListUiState())
    val uiRecipes: StateFlow<RecipeListUiState> = _uiRecipes

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        _uiRecipes.value = RecipeListUiState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {

            val result = repository.getAllRecipes()

            if (result.isSuccess) {
                val recipes = result.getOrNull()
                if (recipes != null) {
                    val recipeUiDataList = recipes.map {
                        RecipeListUiData(
                            id = it.id,
                            title = it.title,
                            image = it.image,
                            summary = it.summary
                        )

                    }
                    _uiRecipes.value = RecipeListUiState(list = recipeUiDataList)
                }
            } else {
                val ex = result.exceptionOrNull()
                if (ex is UnknownHostException) {
                    _uiRecipes.value = RecipeListUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )
                } else {
                    _uiRecipes.value = RecipeListUiState(isError = true)
                }
            }
        }
    }


companion object {
    val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

            val application = checkNotNull(extras[APPLICATION_KEY])
            return MainViewModel(
                repository = (application as EasyRecipesApplication).repository
            ) as T
        }
    }
}
}