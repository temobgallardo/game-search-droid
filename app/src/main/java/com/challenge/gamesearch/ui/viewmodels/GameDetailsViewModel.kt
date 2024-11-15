package com.challenge.gamesearch.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.gamesearch.domain.models.Game
import com.challenge.gamesearch.domain.repositories.IGameRepository
import com.challenge.gamesearch.ui.GAME_ID_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameDetailsViewModel @Inject constructor(
    savedStateHandler: SavedStateHandle,
    private val gameRepository: IGameRepository) :
    ViewModel() {
        private val gameId: String = checkNotNull(savedStateHandler[GAME_ID_KEY])

    val _stateGameDetails = MutableStateFlow(StateGameDetails())
    val stateGameDetails: StateFlow<StateGameDetails>
        get() = _stateGameDetails.asStateFlow()

    init{
        initilizeStates()
    }

    private fun initilizeStates() {
        viewModelScope.launch {
            gameRepository.getGameById(gameId).collect { game ->
                _stateGameDetails.update {
                    it.copy(
                        game = game
                    )
                }
            }
        }
    }
}

data class StateGameDetails(
    val title: String = "",
    val game: Game? = null,
)
