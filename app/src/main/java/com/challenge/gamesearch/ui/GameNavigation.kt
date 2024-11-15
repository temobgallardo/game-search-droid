package com.challenge.gamesearch.ui

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.challenge.gamesearch.ui.viewmodels.GameDetailsViewModel
import com.challenge.gamesearch.ui.viewmodels.GameSearchViewModel
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

fun NavGraphBuilder.gameDetailsScreen(){
    composable<GameNavigation.Game>{
        val viewModel : GameDetailsViewModel = hiltViewModel<GameDetailsViewModel>()
        val uiState = viewModel.stateGameDetails.collectAsStateWithLifecycle().value

        GameDetailsView(
            gameDetailsStates = uiState
        )
    }
}

