package com.devspace.myapplication.main

import com.devspace.myapplication.common.model.Recipe
import com.devspace.myapplication.main.data.RecipeMainRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

class MainRepositoryTest {

    private val local = FakeMainLocalDataSource()
    private val remote = FakeMainRemoteDataSource()

    private val underTest by lazy {
        RecipeMainRepository(local, remote)
    }

    @Test
    fun `Given result is success When get all recipes Then update local items`() {
        runTest {

            val list = listOf(
                Recipe(
                    id = 1,
                    title = "title1",
                    image = "image1",
                    summary = "summary1"
                )
            )

            remote.recipes = Result.success(list)
            local.recipes = list

            val result = underTest.getAllRecipes()

            val expected = Result.success(list)

            assertEquals(expected, result)
            assertEquals(local.update, list)
        }
    }

    @Test
    fun `Given no internet connection and no local data When get all recipes Then return remote result`() {
        runTest {

            val resultRemote = Result.failure<List<Recipe>>(UnknownHostException())

            //Given
            remote.recipes = resultRemote
            local.recipes = emptyList()

            //When
            val result = underTest.getAllRecipes()

            //Then
            val expected = resultRemote

            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given no internet connection When get all recipes Then return local data`() {
        runTest {

            val list = listOf(
                Recipe(
                    id = 1,
                    title = "title1",
                    image = "image1",
                    summary = "summary1"
                )
            )

            //Given
            remote.recipes = Result.failure(UnknownHostException())
            local.recipes = list

            //When
            val result = underTest.getAllRecipes()

            //Then
            val expected = Result.success(list)
            assertEquals(expected, result)
        }
    }

    @Test
    fun `Given unknown error When get all recipes Then return remote result`() {
        runTest {

            val remoteResult = Result.failure<List<Recipe>>(UnknownError())
            //Given
            remote.recipes = remoteResult
            local.recipes = emptyList()

            //When
            val result = underTest.getAllRecipes()

            //Then
            val expected = remoteResult

            assertEquals(expected, result)
        }
    }
}