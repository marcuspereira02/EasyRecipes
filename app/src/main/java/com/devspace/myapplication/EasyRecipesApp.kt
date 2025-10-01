package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun EasyRecipesApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboardingScreen") {
        composable(route = "onboardingScreen"){
            OnboardingScreen(navController)
        }
        composable(route = "mainScreen"){
            MainScreen(navController)
        }
        composable(route = "detailScreen" + "/{itemId}",
            arguments = listOf(navArgument("itemId"){
                type= NavType.StringType
            })
        ){backStackEntry->
            val movieId = requireNotNull( backStackEntry.arguments?.getString("itemId"))
            DetailScreen(movieId, navController)
        }
    }

}