package com.challenge.gamesearch.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.challenge.gamesearch.ui.viewmodels.GameDetailsViewModel
import kotlinx.serialization.Serializable

@Serializable
object GameNavigation {
    @Serializable
    data class Game(val gameId: String)
}

const val GAME_ID_KEY = "gameId"

fun NavController.navigateToGameDetails(gameId: String, navigation: NavOptions? = null){
    navigate(GameNavigation.Game(gameId), navigation )
}

fun NavGraphBuilder.gameDetailsScreen(navigateBack: () -> Unit){
    composable<GameNavigation.Game>{
        val viewModel : GameDetailsViewModel = hiltViewModel<GameDetailsViewModel>()
        val uiState = viewModel.gameDetailStates.collectAsStateWithLifecycle().value

        GameDetailsView(
            gameDetailStates = uiState,
            viewModel = viewModel,
            navigateBack = navigateBack
        )
    }
}

