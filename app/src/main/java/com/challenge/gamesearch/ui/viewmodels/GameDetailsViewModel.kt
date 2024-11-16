package com.challenge.gamesearch.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
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
    private val gameRepository: IGameRepository
) :
    ViewModel() {
    private val gameId: String = checkNotNull(savedStateHandler[GAME_ID_KEY])

    private val _GameDetailStates = MutableStateFlow(GameDetailStates())
    val gameDetailStates: StateFlow<GameDetailStates>
        get() = _GameDetailStates.asStateFlow()

    init {
        initilizeStates()
    }

    fun onEvent(event: GameDetailEvents) {
        when (event) {
            is GameDetailEvents.UpdateGameDescription -> updateGameDescription(event.description)
            is GameDetailEvents.UpdateGameDeveloper -> updateGameDeveloper(event.developer)
            is GameDetailEvents.UpdateGameGenre -> updateGameGenre(event.genre)
            is GameDetailEvents.UpdateGamePlatform -> updateGamePlatform(event.platform)
            is GameDetailEvents.UpdateGamePublisher -> updateGamePublisher(event.publisher)
            is GameDetailEvents.UpdateGameTitle -> updateGameTitle(event.title)
            is GameDetailEvents.RemoveGame -> removeGame(event.game)
            is GameDetailEvents.UpdateGame -> updateGame(event.game)
        }
    }

    private fun updateGame(game: Game?) {
        viewModelScope.launch {
            game?.let {
                gameRepository.update(game)
            }
        }
    }

    private fun removeGame(game: Game?) {
        viewModelScope.launch {
            game?.let {
                gameRepository.delete(game)
            }
        }
    }

    private fun updateGameTitle(title: String) {
        _GameDetailStates.update { state ->
            state.copy(game = _GameDetailStates.value.game?.copy(title = title))
        }
        updateGame(_GameDetailStates.value.game)
    }

    private fun updateGamePublisher(publisher: String) {
        _GameDetailStates.update { state ->
            state.copy(game = _GameDetailStates.value.game?.copy(publisher = publisher))
        }
        updateGame(_GameDetailStates.value.game)
    }

    private fun updateGamePlatform(platform: String) {
        _GameDetailStates.update { state ->
            state.copy(game = _GameDetailStates.value.game?.copy(platform = platform))
        }
        updateGame(_GameDetailStates.value.game)
    }

    private fun updateGameGenre(genre: String) {
        _GameDetailStates.update { state ->
            state.copy(game = _GameDetailStates.value.game?.copy(genre = genre))
        }
        updateGame(_GameDetailStates.value.game)
    }

    private fun updateGameDeveloper(developer: String) {
        _GameDetailStates.update { state ->
            state.copy(game = _GameDetailStates.value.game?.copy(developer = developer))
        }
        updateGame(_GameDetailStates.value.game)
    }

    private fun updateGameDescription(description: String) {
        _GameDetailStates.update { state ->
            state.copy(game = _GameDetailStates.value.game?.copy(description = description))
        }
        updateGame(_GameDetailStates.value.game)
    }

    private fun initilizeStates() {
        viewModelScope.launch {
            gameRepository.getGameById(gameId).collect { game ->
                _GameDetailStates.update {
                    it.copy(game = game)
                }
            }
        }
    }
}

data class GameDetailStates(
    val title: String = "",
    val game: Game? = null
)

sealed interface GameDetailEvents {
    data class UpdateGameTitle(val title: String) : GameDetailEvents
    data class UpdateGameGenre(val genre: String) : GameDetailEvents
    data class UpdateGamePlatform(val platform: String) : GameDetailEvents
    data class UpdateGameDeveloper(val developer: String) : GameDetailEvents
    data class UpdateGamePublisher(val publisher: String) : GameDetailEvents
    data class UpdateGameDescription(val description: String) : GameDetailEvents
    data class UpdateGame(val game: Game?) : GameDetailEvents
    data class RemoveGame(val game: Game?) : GameDetailEvents
}