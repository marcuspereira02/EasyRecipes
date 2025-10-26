package com.devspace.myapplication.search.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.remote.RetrofitClient
import com.devspace.myapplication.search.data.SearchRecipeDto
import com.devspace.myapplication.search.data.SearchService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchService: SearchService
) : ViewModel(){

    private val _uiRecipesFound = MutableStateFlow<List<SearchRecipeDto>>(emptyList())
    val uiRecipesFound : StateFlow<List<SearchRecipeDto>> = _uiRecipesFound

    fun fetchRecipesFound(querySearch: String){

        viewModelScope.launch(Dispatchers.IO) {
            val response = searchService.searchRecipes(querySearch)
            if (response.isSuccessful) {
                val recipes =  response.body()?.results ?: emptyList()
                _uiRecipesFound.value = recipes
            } else {
                Log.d(
                    "SearchScreen",
                    "Request error: ${response.code()} - ${response.message()}"
                )
                Log.d("SearchScreen", "Request error: ${response.errorBody()?.string()}")
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