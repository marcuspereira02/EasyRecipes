package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreen() {
    var randomRecipes by rememberSaveable { mutableStateOf<List<RecipeDto>>(emptyList()) }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

    if (randomRecipes.isEmpty()) {
        apiService.getRecipesRandom().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse?>,
                response: Response<RecipeResponse?>
            ) {
                if (response.isSuccessful) {
                    randomRecipes = response.body()?.recipes ?: emptyList()
                } else {
                    Log.d("MainScreen", "Request error: ${response.errorBody()}")
                }
            }

            override fun onFailure(
                call: Call<RecipeResponse?>,
                t: Throwable
            ) {
                Log.d("MainScreen", "Network Error :: ${t.message}")
            }

        })

    }
    Surface(modifier = Modifier.fillMaxSize()) {
        MainContent(randomRecipes = randomRecipes) { itemClicked ->

        }
    }


}

@Composable
private fun MainContent(
    randomRecipes: List<RecipeDto>,
    onClick: (RecipeDto) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Text(
            modifier = Modifier.padding(16.dp),
            text = "Recipes",
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )

        recipeList(
            recipeList = randomRecipes,
            onClick = onClick
        )
        Log.d("MainContent", "Recipes size: ${randomRecipes.size}")
    }


}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {

    EasyRecipesTheme {
        // MainContent()
    }

}

@Composable
private fun recipeList(
    recipeList: List<RecipeDto>, onClick: (RecipeDto) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(recipeList) {
            recipeItem(
                recipeDto = it, onClick = onClick
            )
        }
    }

}

@Composable
private fun recipeItem(recipeDto: RecipeDto, onClick: (RecipeDto) -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.Crop,
            model = recipeDto.image,
            contentDescription = "${recipeDto.title} image"
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = recipeDto.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        )
        Text(
            modifier = Modifier.padding(4.dp),
            text = recipeDto.summary,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
    }
}