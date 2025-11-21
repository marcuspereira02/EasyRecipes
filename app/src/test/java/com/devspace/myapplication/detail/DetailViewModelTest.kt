package com.devspace.myapplication.detail

import app.cash.turbine.test
import com.devspace.myapplication.common.data.remote.RecipeDto

import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailUiData
import com.devspace.myapplication.detail.presentation.ui.RecipeDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.test.assertEquals

class DetailViewModelTest {

    private val service = FakeDetailService()

    private val underTest by lazy {
        DetailViewModel(service, Dispatchers.Unconfined)
    }

    @Test
    fun `Given response is success When get recipe detail Then return recipe ui data`() {
        runTest {
            val recipe = RecipeDto(
                id = 1,
                title = "title1",
                summary = "summary1",
                image = "image1"
            )

            service.recipeByIdResponse = Response.success(recipe)

            underTest.fetchRecipeDetail("1")

            underTest.uiRecipe.test {
                val expected = RecipeDetailUiState(
                    data = RecipeDetailUiData(
                        id = 1,
                        title = "title1",
                        summary = "summary1",
                        image = "image1"
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
    fun `Given recipe is null When get recipe detail Then return message error`() {
        runTest {

            service.recipeByIdResponse = Response.error(
                404,
                "Not Found".toResponseBody("application/json".toMediaType())
            )

            underTest.fetchRecipeDetail("1")

            underTest.uiRecipe.test {
                val expected = RecipeDetailUiState(
                    data = null,
                    isLoading = false,
                    isError = true,
                    errorMessage = "Something went wrong"
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given no internet When get recipe detail Then return error message`() {
        runTest {

            service.recipeByIdException = UnknownHostException()

            underTest.fetchRecipeDetail("1")
            underTest.uiRecipe.test {
                val expected = RecipeDetailUiState(
                    data = null,
                    isLoading = false,
                    isError = true,
                    errorMessage = "Not internet connection"
                )
                assertEquals(expected, awaitItem())
            }
        }
    }

    @Test
    fun `Given unknown error When get recipe detail Then return error message`() {
        runTest {
            service.recipeByIdException = RuntimeException("error")

            underTest.fetchRecipeDetail("1")
            underTest.uiRecipe.test {
                val expected = RecipeDetailUiState(
                    data = null,
                    isLoading = false,
                    isError = true,
                    errorMessage = "Something went wrong"
                )
                assertEquals(expected, awaitItem())
            }
        }
    }
}