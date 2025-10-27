package com.devspace.myapplication.detail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.devspace.myapplication.componentes.ERHtmlText
import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

@Composable
fun DetailScreen(id: String, navController: NavHostController, viewModel: DetailViewModel) {

    val recipe by viewModel.uiRecipe.collectAsState()

    LaunchedEffect(id) {
        viewModel.fetchRecipeDetail(id)
    }

    when {
        recipe.isLoading -> {
            Column {
                RecipeDetailHeader(
                    navHostController = navController,
                    viewModel = viewModel,
                    title = "LOADING!"
                )

                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Loading...",
                    fontSize = 16.sp
                )
            }
        }

        recipe.isError -> {
            Column {
                RecipeDetailHeader(
                    navHostController = navController,
                    viewModel = viewModel,
                    title = "ERROR!"
                )
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = recipe.errorMessage ?: "",
                    fontSize = 16.sp,
                    color = Color.Red
                )
            }
        }

        recipe.data != null -> {
            Column (modifier = Modifier.verticalScroll(rememberScrollState())) {
                RecipeDetailHeader(
                    navHostController = navController,
                    viewModel = viewModel,
                    title = recipe.data!!.title
                )
                DetailContent(recipe.data)
            }
        }
    }

}

@Composable
private fun DetailContent(recipe: RecipeDetailUiData?) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Fit,
            model = recipe?.image,
            contentDescription = "${recipe!!.title} image"
        )
        ERHtmlText(recipe.summary)
    }
}

@Composable
private fun RecipeDetailHeader(
    navHostController: NavHostController,
    viewModel: DetailViewModel, title: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                viewModel.cleanRecipeId()
                navHostController.popBackStack()
            }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    "Back Button"
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = 8.dp),
                text = title,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {

    EasyRecipesTheme {
        val recipe = RecipeDetailUiData(
            id = 1,
            title = "title",
            image = "2334",
            summary = "long text,long text,long text,long text,long text,long text"
        )
        DetailContent(recipe)
    }
}