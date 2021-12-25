package com.mbakgun.restaurants.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mbakgun.restaurants.navigation.Screen
import com.mbakgun.restaurants.ui.screen.RestaurantsActivityScreen

@Composable
fun RestaurantsApp(){
    val navigation = rememberNavController()
    
    NavHost(
        navController = navigation,
        startDestination = Screen.RestaurantsScreen.route
    ){
        composable(
            route = Screen.RestaurantsScreen.route
        ) {
            RestaurantsActivityScreen()
        }
    }
}