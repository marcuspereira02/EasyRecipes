package com.devspace.myapplication.search


import app.cash.turbine.test
import com.devspace.myapplication.common.model.RecipeListUiData
import com.devspace.myapplication.common.model.RecipeListUiState
import com.devspace.myapplication.search.data.SearchRecipeDto
import com.devspace.myapplication.search.data.SearchRecipesResponse
import com.devspace.myapplication.search.presentation.SearchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.Response
import java.net.UnknownHostException
import kotlin.test.assertEquals

class SearchViewModelTest {

    private val service = FakeSearchService()

    private val underTest by lazy {
        SearchViewModel(service, Dispatchers.Unconfined)
    }

    @Test
    fun `Given response is success When search Then return recipes`() {
        runTest {
            val recipes = listOf(
                SearchRecipeDto(
                    id = 1,
                    title = "title1",
                    image = "image1",
                )
            )

            service.recipes = Response.success(SearchRecipesResponse(recipes))

            val query = "Lasagna"
            underTest.fetchRecipesFound(query)

            underTest.uiRecipesFound.test {

                val expected = RecipeListUiState(
                    list = listOf(
                        RecipeListUiData(
                            id = 1,
                            title = "title1",
                            image = "image1",
                            summary = ""
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
    fun `Given recipes is null When search recipes Then return message error`() {
        runTest {
            service.recipes = Response.error(
                404,
                "Not Found".toResponseBody("application/json".toMediaType())
            )

            underTest.fetchRecipesFound("Lasagna")

            underTest.uiRecipesFound.test {
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

    @Test
    fun `Given no internet When search recipes Then return message not internet connection`() {
        runTest {
            service.recipeByQueryException = UnknownHostException()

            underTest.fetchRecipesFound("Lasagna")
            underTest.uiRecipesFound.test {
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
    fun `Given unknown error When get recipe detail Then return error message`() {
        runTest {
            service.recipeByQueryException = RuntimeException("error")

            underTest.fetchRecipesFound("Lasagna")
            underTest.uiRecipesFound.test {
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