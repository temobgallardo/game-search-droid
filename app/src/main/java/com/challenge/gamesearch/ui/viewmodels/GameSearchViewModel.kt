package com.challenge.gamesearch.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.gamesearch.data.repositories.GameFilters
import com.challenge.gamesearch.domain.models.Game
import com.challenge.gamesearch.domain.repositories.IGameRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameSearchViewModel @Inject constructor(private val gameRepository: IGameRepository) :
    ViewModel() {
    private var games = emptyList<Game>()

    private val _gamesUiStates = MutableStateFlow(GameUIState())
    val gamesUiStates: StateFlow<GameUIState>
        get() = _gamesUiStates.asStateFlow()

    init {
        getGames()
        initializeGamesDatabase()
    }

    fun onEvent(event: GameListEvent) {
        when (event) {
            is GameListEvent.FilterByName -> filterByName(event.name)
            is GameListEvent.FilterBySubCategory -> filterBySubCategory(event.subCategory)
            is GameListEvent.SetCategory -> setCategory(event.category)
        }

    }

    private fun setCategory(category: String) {
        _gamesUiStates.update {
            it.copy(
                categorySelected = category,
                subCategories = it.categories[category]?.toList() ?: emptyList(),
                games = games,
                subCategorySelected = null
            )
        }
    }

    private fun filterByName(name: String) {
        _gamesUiStates.update {
            it.copy(toSearch = name)
        }

        if (name.isEmpty()) {
            _gamesUiStates.update { state -> state.copy(games = games, error = null) }
        } else {
            val games =
                _gamesUiStates.value.games.filter { it.title.contains(name, ignoreCase = true) }
            _gamesUiStates.update { state -> state.copy(games = games, error = null) }
        }
    }

    private fun filterBySubCategory(subCategory: String?) {
        Log.d("INFO", "Game States | Subcategory Selected = ${_gamesUiStates.value.subCategorySelected} | Actual = $subCategory")
        if (subCategory == null) {
            _gamesUiStates.update { state ->
                state.copy(
                    games = games, subCategorySelected = null, error = null
                )
            }
            return
        }

        if (subCategory.isBlank()) {
            _gamesUiStates.update { state -> state.copy(games = games, error = null) }
        } else {
            val games = when (_gamesUiStates.value.categorySelected) {
                GameFilters.Genre.name -> games.filter {
                    it.genre.contains(
                        subCategory,
                        ignoreCase = true
                    )
                }

                GameFilters.Platform.name -> games.filter {
                    it.platform.contains(
                        subCategory,
                        ignoreCase = true
                    )
                }

                GameFilters.Developer.name -> games.filter {
                    it.developer.contains(
                        subCategory,
                        ignoreCase = true
                    )
                }

                GameFilters.Publisher.name -> games.filter {
                    it.publisher.contains(
                        subCategory,
                        ignoreCase = true
                    )
                }

                else -> games
            }

            _gamesUiStates.update { state ->
                state.copy(
                    games = games, subCategorySelected = subCategory, error = null
                )
            }
        }
    }

    private fun initializeCategories(games: List<Game>) {
        _gamesUiStates.update { state ->
            state.copy(
                categories = hashMapOf(
                    GameFilters.All.name to emptySet(),
                    GameFilters.Genre.name to games.map { it.genre }.toSet(),
                    GameFilters.Platform.name to games.map { it.platform }.toSet(),
                    GameFilters.Publisher.name to games.map { it.publisher }.toSet(),
                    GameFilters.Developer.name to games.map { it.developer }.toSet(),
                )
            )
        }
    }

    private fun getGames() {
        viewModelScope.launch {
            gameRepository.getGames().collect { gs ->
                games = gs
                _gamesUiStates.update { it.copy(games = games) }
                initializeCategories(games)
            }
        }
    }

    private fun initializeGamesDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.fetchOrRequestGames()
        }
    }

}

data class GameUIState(
    val toSearch: String = "",
    val games: List<Game> = emptyList(),
    val isLoading: Boolean = false,
    val categories: HashMap<String, Set<String>> = hashMapOf(),
    val categorySelected: String? = null,
    val subCategories: List<String> = emptyList(),
    val subCategorySelected: String? = null,
    val error: String? = null
)

sealed interface GameListEvent {
    data class FilterByName(val name: String) : GameListEvent
    data class FilterBySubCategory(val subCategory: String?) : GameListEvent
    data class SetCategory(val category: String) : GameListEvent
}