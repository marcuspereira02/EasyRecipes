package com.devspace.myapplication.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.common.model.RecipeListUiData
import com.devspace.myapplication.common.model.RecipeListUiState
import com.devspace.myapplication.search.data.SearchService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class SearchViewModel(
    private val searchService: SearchService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(){

    private val _uiRecipesFound = MutableStateFlow(RecipeListUiState())
    val uiRecipesFound : StateFlow<RecipeListUiState> = _uiRecipesFound

    fun fetchRecipesFound(querySearch: String){

        _uiRecipesFound.value = RecipeListUiState(isLoading = true)
        viewModelScope.launch(dispatcher) {
            try {
                val response = searchService.searchRecipes(querySearch)
                if (response.isSuccessful) {
                    val recipes =  response.body()?.results ?: emptyList()
                    val recipesUiData = recipes.map {
                        RecipeListUiData(
                            id = it.id,
                            title = it.title,
                            image = it.image,
                            summary = ""
                        )
                    }
                    _uiRecipesFound.value = RecipeListUiState(list = recipesUiData)

                } else {
                    _uiRecipesFound.value = RecipeListUiState(isError = true)
                }
            } catch (ex: Exception){
                if (ex is UnknownHostException){
                    _uiRecipesFound.value = RecipeListUiState(isError = true,
                        errorMessage = "Not internet connection")
                } else{
                    _uiRecipesFound.value = RecipeListUiState(isError = true)
                }
            }
        }
    }

    companion object{
        val Factory : ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val searchService = RetrofitClient.retrofitInstance.create(SearchService::class.java)
                return SearchViewModel(
                    searchService
                ) as T
            }
        }
    }
}