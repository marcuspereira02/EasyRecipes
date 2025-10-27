package com.devspace.myapplication.search.presentation.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.model.RecipeListUiData
import com.devspace.myapplication.common.model.RecipeListUiState
import com.devspace.myapplication.search.presentation.SearchViewModel

@Composable
fun SearchScreen(
    querySearch: String,
    navController: NavHostController,
    searchViewModel: SearchViewModel
) {

    val recipesFound by searchViewModel.uiRecipesFound.collectAsState()

    LaunchedEffect(querySearch) {
        searchViewModel.fetchRecipesFound(querySearch)
    }

    Column {
        SearchRecipeHeader(
            navController = navController,
            querySearch = querySearch
        )

        when {
            recipesFound.isLoading -> {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Loading...",
                    fontSize = 16.sp
                )
            }

            recipesFound.isError -> {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    color = Color.Red,
                    text = recipesFound.errorMessage ?: "",
                )
            }

            else -> {
                SearchContent(recipesFound, onClick = { itemClicked ->
                    navController.navigate(route = "detailScreen/${itemClicked.id}")
                })

            }
        }
    }
}


@Composable
private fun SearchContent(
    recipeList: RecipeListUiState,
    onClick: (RecipeListUiData) -> Unit
) {
    RecipeList(recipeList, onClick)
}

@Composable
private fun RecipeList(
    recipeList: RecipeListUiState,
    onClick: (RecipeListUiData) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(recipeList.list) {
            ItemRecipe(
                recipe = it, onClick = onClick
            )
        }
    }
}

@Composable
private fun ItemRecipe(
    recipe: RecipeListUiData, onClick: (RecipeListUiData) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick.invoke(recipe)
            }) {

        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp),
            text = recipe.title,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.size(8.dp))

        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 8.dp)
                .height(150.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )
    }
}

@Composable
private fun SearchRecipeHeader(
    navController: NavHostController,
    querySearch: String,
) {
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
}

@Preview(showBackground = true)
@Composable
private fun SearchPreview() {

    val recipe = RecipeListUiData(
        id = 0,
        title = "Title",
        image = "10203",
        summary = ""
    )
    ItemRecipe(recipe) { }
}