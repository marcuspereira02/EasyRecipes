package com.devspace.myapplication.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MainScreen() {
    MainContent()
}

@Composable
private fun MainContent() {
    Text(text = "MainScreen")
}

@Preview(showBackground = true)
@Composable
private fun MainPreview() {

    EasyRecipesTheme {
        MainContent()
    }

}