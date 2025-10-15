package com.devspace.myapplication.detail.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.devspace.myapplication.common.model.RecipeDto
import com.devspace.myapplication.componentes.ERHtmlText
import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

@Composable
fun DetailScreen(id: String, navController: NavHostController, viewModel: DetailViewModel) {

    val recipe by viewModel.uiRecipe.collectAsState()
    viewModel.fetchRecipeDetail(id)

    recipe?.let {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    viewModel.cleanRecipeId()
                    navController.popBackStack()
                }){
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        "Back button"
                    )
                }
                Text(modifier = Modifier.padding(start = 4.dp),
                    text = it.title,
                    fontWeight = FontWeight.Bold)
            }
            DetailContent(it)
        }
    }
}

@Composable
private fun DetailContent(recipe: RecipeDto) {
    Column(modifier = Modifier.fillMaxSize()) {
        AsyncImage(
            modifier = Modifier
                .height(200.dp)
                .fillMaxSize()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            model = recipe.image,
            contentDescription = "${recipe.title} image"
        )
        ERHtmlText(recipe.summary)
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailPreview() {

    EasyRecipesTheme {
        val recipe = RecipeDto(
            id = 1,
            title = "title",
            image = "2334",
            summary = "long text,long text,long text,long text,long text,long text"
        )
        DetailContent(recipe)
    }
}