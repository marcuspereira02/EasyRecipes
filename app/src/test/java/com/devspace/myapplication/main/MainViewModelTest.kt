package com.devspace.myapplication.main

import app.cash.turbine.test
import com.devspace.myapplication.common.model.Recipe
import com.devspace.myapplication.common.model.RecipeListUiData
import com.devspace.myapplication.common.model.RecipeListUiState
import com.devspace.myapplication.main.presentation.MainViewModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class MainViewModelTest {
    private val repository = FakeMainRepository()

    private val underTest by lazy {
        MainViewModel(repository)
    }

    @Test
    fun `Given fresh viewModel When collecting recipes Then assert expected value`() {
        runTest {
            val list = listOf(
                Recipe(
                    id = 1,
                    title = "title1",
                    summary = "summary1",
                    image = "image1"
                )
            )

            repository.recipes = Result.success(list)

            underTest.uiRecipes.test {
                val expected = RecipeListUiState(
                    list = listOf(
                        RecipeListUiData(
                            id = 1,
                            title = "title1",
                            summary = "summary1",
                            image = "image1"
                        )
                    ),
                    isLoading = false,
                    isError = false,
                    errorMessage = "Something went wrong"
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given no internet When get recipes Then emits network error`() {
        runTest {
            repository.recipes = Result.failure(UnknownHostException())

            underTest.uiRecipes.test {
                val expected = RecipeListUiState(
                    list = emptyList(),
                    isLoading = false,
                    isError = true,
                    errorMessage = "Not internet connection"
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given unknown exception When recipes Then emits unknown error`() {
        runTest {
            repository.recipes = Result.failure(UnknownError())

            underTest.uiRecipes.test {
                val expected = RecipeListUiState(
                    list = emptyList(),
                    isLoading = false,
                    isError = true,
                    errorMessage = "Something went wrong"
                )
                assertEquals(expected, awaitItem())
            }

        }
    }

}