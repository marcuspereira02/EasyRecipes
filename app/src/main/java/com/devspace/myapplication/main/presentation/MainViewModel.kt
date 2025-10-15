package com.devspace.myapplication.main.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.devspace.myapplication.common.data.RetrofitClient
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.main.data.MainService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class MainViewModel(
    private val mainService: MainService
) : ViewModel() {

    private val _uiRecipes = MutableStateFlow<List<RecipeDto>>(emptyList())
    val uiRecipes: StateFlow<List<RecipeDto>> = _uiRecipes

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = mainService.getRecipesRandom()

            if (response.isSuccessful) {
                val recipes = response.body()?.recipes ?: emptyList()
                _uiRecipes.value = recipes
            } else {
                Log.d(
                    "MainScreen",
                    "Request error: ${response.code()} - ${response.message()}"
                )
                Log.d("MainScreen", "Request error: ${response.errorBody()?.string()}")
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val mainService = RetrofitClient.retrofitInstance.create(MainService::class.java)
                return MainViewModel(
                    mainService
                ) as T
            }
        }
    }
}