package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devspace.myapplication.detail.presentation.DetailViewModel
import com.devspace.myapplication.detail.presentation.ui.DetailScreen
import com.devspace.myapplication.main.presentation.MainViewModel
import com.devspace.myapplication.main.presentation.ui.MainScreen
import com.devspace.myapplication.onboarding.OnboardingScreen
import com.devspace.myapplication.search.presentation.SearchViewModel
import com.devspace.myapplication.search.presentation.ui.SearchScreen

@Composable
fun EasyRecipesApp(
    detailViewModel: DetailViewModel,
    mainViewModel: MainViewModel,
    searchViewModel: SearchViewModel
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboardingScreen") {
        composable(route = "onboardingScreen") {
            OnboardingScreen(navController)
        }
        composable(route = "mainScreen") {
            MainScreen(navController, mainViewModel)
        }
        composable(
            route = "detailScreen" + "/{itemId}",
            arguments = listOf(navArgument("itemId") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val recipeId = requireNotNull(backStackEntry.arguments?.getString("itemId"))
            DetailScreen(recipeId, navController, detailViewModel)
        }
        composable(
            route = "searchScreen" + "/{query}",
            arguments = listOf(navArgument("query") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val querySearch = requireNotNull(backStackEntry.arguments?.getString("query"))
            SearchScreen(querySearch, navController, searchViewModel)
        }

    }

}