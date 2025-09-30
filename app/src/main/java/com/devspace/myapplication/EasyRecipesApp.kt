package com.devspace.myapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.devspace.myapplication.MainScreen

@Composable
fun EasyRecipesApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "onboardingScreen") {
        composable(route = "onboardingScreen"){
            OnboardingScreen(navController)
        }
        composable(route = "mainScreen"){
            MainScreen()
        }
    }

}