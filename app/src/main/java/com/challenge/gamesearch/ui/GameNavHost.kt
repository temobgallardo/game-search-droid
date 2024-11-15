package com.challenge.gamesearch.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun GameNavHost(
    modifier: Modifier,
    navController: NavHostController
){
    NavHost(modifier = modifier, navController = navController, startDestination = GameHome){
        gameHome(navigateToGameDetails = navController::navigateToGameDetails)
        gameDetailsScreen()
    }

}