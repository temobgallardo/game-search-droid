package com.challenge.gamesearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.challenge.gamesearch.ui.viewmodels.GameDetailEvents
import com.challenge.gamesearch.ui.viewmodels.GameDetailStates
import com.challenge.gamesearch.ui.viewmodels.GameDetailsViewModel

@Composable
fun GameDetailsView(
    modifier: Modifier = Modifier,
    gameDetailStates: GameDetailStates,
    viewModel: GameDetailsViewModel,
    navigateBack: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(gameDetailStates.game?.thumbnail),
            contentDescription = gameDetailStates.game?.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(shape = RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(4.dp))
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = gameDetailStates.game?.title ?: "",
            onValueChange = { viewModel.onEvent(GameDetailEvents.UpdateGameTitle(it)) },
            label = { Text("Title") }
        )
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = gameDetailStates.game?.genre ?: "",
            onValueChange = { viewModel.onEvent(GameDetailEvents.UpdateGameGenre(it)) },
            label = { Text("Genre") })
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = gameDetailStates.game?.platform ?: "",
            onValueChange = { viewModel.onEvent(GameDetailEvents.UpdateGamePlatform(it)) },
            label = { Text("Platform") })
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = gameDetailStates.game?.publisher ?: "",
            onValueChange = { viewModel.onEvent(GameDetailEvents.UpdateGamePublisher(it)) },
            label = { Text("Publisher") })
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = gameDetailStates.game?.developer ?: "",
            onValueChange = { viewModel.onEvent(GameDetailEvents.UpdateGameDeveloper(it)) },
            label = { Text("Developer") })
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            value = gameDetailStates.game?.description ?: "",
            onValueChange = { viewModel.onEvent(GameDetailEvents.UpdateGameDescription(it)) },
            label = { Text("Description") })
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = modifier)
        {
            Button(onClick = {
                viewModel.onEvent(GameDetailEvents.UpdateGame(gameDetailStates.game))
            }) {
                Text(
                    text = "Update"
                )
            }
            Button(onClick = {
                viewModel.onEvent(GameDetailEvents.RemoveGame(gameDetailStates.game))
                navigateBack()
            }) {
                Text(
                    text = "Remove"
                )
            }
        }
    }
}