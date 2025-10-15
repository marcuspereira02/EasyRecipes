package com.devspace.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.main.presentation.MainViewModel
import com.devspace.myapplication.search.presentation.SearchViewModel
import com.devspace.myapplication.ui.theme.EasyRecipesTheme

class MainActivity : ComponentActivity() {

    private val detailViewModel by viewModels<DetailViewModel> { DetailViewModel.Factory }
    private val mainViewModel by viewModels<MainViewModel> { MainViewModel.Factory }
    private val searchViewModel by viewModels <SearchViewModel>{ SearchViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyRecipesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                 EasyRecipesApp(
                     detailViewModel,
                     mainViewModel,
                     searchViewModel
                     )
                }
            }
        }
    }
}
