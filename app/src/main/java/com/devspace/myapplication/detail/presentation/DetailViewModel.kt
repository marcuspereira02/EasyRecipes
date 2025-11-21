package com.devspace.myapplication.detail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.data.remote.RecipeDto
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailUiData
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailUiState
import com.devspace.myapplication.detail.data.DetailService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class DetailViewModel(
    private val detailService: DetailService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiRecipe = MutableStateFlow(RecipeDetailUiState())
    val uiRecipe: StateFlow<RecipeDetailUiState> = _uiRecipe

    fun fetchRecipeDetail(
        id: String
    ) {
        _uiRecipe.value =
            RecipeDetailUiState(isLoading = true)
        viewModelScope.launch(dispatcher) {
            try {
                val response = detailService.getRecipeInformation(id)
                if (response.isSuccessful) {
                    val recipe = response.body()
                    if (recipe != null) {
                        val recipeUiData = converterRecipeDto(recipe)
                        _uiRecipe.value = RecipeDetailUiState(data = recipeUiData)
                    }
                } else {
                    _uiRecipe.value = RecipeDetailUiState(isError = true)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                if (ex is UnknownHostException) {
                    _uiRecipe.value = RecipeDetailUiState(
                        isError = true,
                        errorMessage = "Not internet connection"
                    )
                } else {
                    _uiRecipe.value = RecipeDetailUiState(isError = true)
                }
            }
        }
    }

    fun cleanRecipeId() {
        viewModelScope.launch {
            _uiRecipe.value = RecipeDetailUiState()
        }
    }

    private fun converterRecipeDto(recipeDto: RecipeDto): RecipeDetailUiData {
        val recipeUiData =
            RecipeDetailUiData(
                id = recipeDto.id,
                title = recipeDto.title,
                image = recipeDto.image,
                summary = recipeDto.summary
            )
        return recipeUiData
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val detailService =
                    RetrofitClient.retrofitInstance.create(DetailService::class.java)
                return DetailViewModel(
                    detailService
                ) as T
            }
        }
    }
}