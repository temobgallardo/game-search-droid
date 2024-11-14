package com.challenge.gamesearch.ui.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.gamesearch.data.repositories.GameFilters
import com.challenge.gamesearch.domain.models.Game
import com.challenge.gamesearch.domain.repositories.IGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSearchViewModel @Inject constructor(private val gameRepository: IGameRepository) :
    ViewModel() {
    // Advice if the search is happening
    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    // Text type by user
    private val _gameToSearch = mutableStateOf("")
    val gameToSearch: State<String>
        get() = _gameToSearch

    private val _games = MutableStateFlow(GameState())
    val games: StateFlow<GameState>
        get() = _games.asStateFlow()


    init {
        getGames()
        initializeGamesDatabase()
    }

    fun updateSearchTerm(gameToSearch: String) {
        _gameToSearch.value = gameToSearch
    }

    fun filterGames(toSearch: String){
        _games
    }

    fun searchByTitle(title: String) {
        viewModelScope.launch {
            gameRepository.filterBy(title, GameFilters.Title).collect { games ->
                _games.update { it.copy(games = games) }
            }
        }
    }
    fun searchByGenre(genre: String) {
        viewModelScope.launch {
            gameRepository.filterBy(genre, GameFilters.Genre).collect { games ->
                _games.update { it.copy(games = games) }
            }
        }
    }
    fun searchByPlatform(platform: String) {
        viewModelScope.launch {
            gameRepository.filterBy(platform, GameFilters.Platform).collect { games ->
                _games.update { it.copy(games = games) }
            }
        }
    }
    fun searchByDeveloper(dev: String) {
        viewModelScope.launch {
            gameRepository.filterBy(dev, GameFilters.Developer).collect { games ->
                _games.update { it.copy(games = games) }
            }
        }
    }
    fun searchByPublisher(publisher: String) {
        viewModelScope.launch {
            gameRepository.filterBy(publisher, GameFilters.Publisher).collect { games ->
                _games.update { it.copy(games = games) }
            }
        }
    }

    private fun getGames() {
        viewModelScope.launch {
            gameRepository.getGames().collect { games ->
                _games.update { it.copy(games = games) }
            }
        }
    }

    private fun initializeGamesDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.fetchOrRequestGames()
        }
    }
}

data class GameState(
    val games: List<Game> = emptyList(),
    val error: String? = null
)