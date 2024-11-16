package com.challenge.gamesearch.data.repositories

import com.challenge.gamesearch.data.IGameApiService
import com.challenge.gamesearch.data.local.database.daos.GameDao
import com.challenge.gamesearch.data.local.database.models.GameDbEntity
import com.challenge.gamesearch.domain.models.Game
import io.mockk.MockKAnnotations
import io.mockk.every
import org.junit.Test
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before

class GameRepositoryTest {
    @MockK
    private lateinit var gameDaoSut: GameDao

    @MockK
    private lateinit var gameApiServive: IGameApiService

    @InjectMockKs
    private lateinit var repositorySut: GameRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun getGames() = runTest {
        val gamesEntities = listOf(
            GameDbEntity(
                id = "1",
                title = "Mystery Adventure",
                thumbnail = "https://example.com/thumbnail1.jpg",
                description = "Explore the hidden secrets in the mysterious world.",
                url = "https://example.com/game1",
                genre = "Adventure",
                platform = "PC",
                publisher = "Great Publisher",
                developer = "Amazing Developer",
                freeToGameUrl = "https://example.com/free1"
            )
        )
        val expectedGames = listOf(
            Game(
                id = "1",
                title = "Mystery Adventure",
                thumbnail = "https://example.com/thumbnail1.jpg",
                description = "Explore the hidden secrets in the mysterious world.",
                url = "https://example.com/game1",
                genre = "Adventure",
                platform = "PC",
                publisher = "Great Publisher",
                developer = "Amazing Developer",
                freeToGameUrl = "https://example.com/free1"
            )
        )

        every { gameDaoSut.getGames() } returns flowOf(gamesEntities)
        val result = repositorySut.getGames().first()

        assert(result == expectedGames)
    }

// TODO: Test the rest of the repository
}