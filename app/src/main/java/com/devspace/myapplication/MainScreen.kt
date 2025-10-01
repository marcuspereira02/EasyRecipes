package com.devspace.myapplication

import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.componentes.ERHtmlText
import com.devspace.myapplication.ui.theme.EasyRecipesTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MainScreen(navController: NavHostController) {
    var recipes by rememberSaveable { mutableStateOf<List<RecipeDto>>(emptyList()) }

    val apiService = RetrofitClient.retrofitInstance.create(ApiService::class.java)

    if (recipes.isEmpty()) {
        apiService.getRecipesRandom().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(
                call: Call<RecipeResponse?>,
                response: Response<RecipeResponse?>
            ) {
                if (response.isSuccessful) {
                    recipes = response.body()?.recipes ?: emptyList()
                } else {
                    Log.d("MainScreen", "Request error: ${response.code()} - ${response.message()}")
                    Log.d("MainScreen", "Request error: ${response.errorBody()?.string()}")
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
        MainContent(recipes = recipes) { itemClicked ->
            navController.navigate("detailScreen/${itemClicked.id}")
        }
    }

}

@Composable
private fun MainContent(
    recipes: List<RecipeDto>,
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
            recipeList = recipes,
            onClick = onClick
        )
        Log.d("MainContent", "Recipes size: ${recipes.size}")
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
            .clickable{
                onClick.invoke(recipeDto)
            }
    ) {
        AsyncImage(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .padding(start = 8.dp,end= 8.dp),
            contentScale = ContentScale.Crop,
            model = recipeDto.image,
            contentDescription = "${recipeDto.title} image"
        )

        Text(
            modifier = Modifier.padding(8.dp),
            text = recipeDto.title,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        ERHtmlText(
            text = recipeDto.summary,
            maxLine = 3
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {

    EasyRecipesTheme {
        MainContent(
            recipes = emptyList(),
            onClick = {

            }
        )
    }

}
