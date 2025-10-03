package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun SearchScreen(querySearch: String, navController: NavHostController) {

    var recipesFound by rememberSaveable { mutableStateOf<List<SearchRecipeDto>>(emptyList()) }
    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

        apiService.searchRecipes(querySearch).enqueue(object : Callback<SearchRecipesResponse> {
            override fun onResponse(
                call: Call<SearchRecipesResponse?>,
                response: Response<SearchRecipesResponse?>
            ) {
                if (response.isSuccessful) {
                    recipesFound = response.body()?.results ?: emptyList()
                } else {
                    Log.d(
                        "SearchScreen",
                        "Request error: ${response.code()} - ${response.message()}"
                    )
                    Log.d("SearchScreen", "Request error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(
                call: Call<SearchRecipesResponse?>,
                t: Throwable
            ) {
                Log.d("MainScreen", "Network Error :: ${t.message}")
            }

        })

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back Button"
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = querySearch
                )
            }

            SearchContent(recipesFound, onClick = { itemClicked ->
                navController.navigate(route = "detailScreen/${itemClicked.id}")
            })
        }
    }



@Composable
private fun SearchContent(
    recipeList: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    RecipeList(recipeList, onClick)
}

@Composable
private fun RecipeList(
    recipeList: List<SearchRecipeDto>,
    onClick: (SearchRecipeDto) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(recipeList) {
            ItemRecipe(
                recipe = it, onClick = onClick
            )
        }
    }
}

@Composable
private fun ItemRecipe(
    recipe: SearchRecipeDto, onClick: (SearchRecipeDto) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                onClick.invoke(recipe)
            }) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp)
                .height(150.dp),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )

        Spacer(modifier = Modifier.size(8.dp))

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            text = recipe.title,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )


    }
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {

    val recipe = SearchRecipeDto(
        id = 0,
        title = "Title",
        image = "10203",
    )

    ItemRecipe(recipe) { }
}