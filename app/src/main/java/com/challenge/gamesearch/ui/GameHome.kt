package com.challenge.gamesearch.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object GameHome

fun NavGraphBuilder.gameHome(navigateToGameDetails: (gameId: String) -> Unit){
    composable<GameHome>{
//        val viewModel : GameDetailsViewModel = hiltViewModel<GameSearchViewModel>()
//        val uiState = viewModel..collectAsStateWithLifecycle().value

        GameSearchView(navigateToGameDetails = navigateToGameDetails)
    }
}
