package com.challenge.gamesearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.challenge.gamesearch.domain.models.Game
import com.challenge.gamesearch.ui.viewmodels.GameSearchViewModel
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import coil.compose.rememberAsyncImagePainter

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GameSearchView(
    modifier: Modifier = Modifier,
    viewModel: GameSearchViewModel = hiltViewModel()
) {
    val textHint = "Type a game to filter the games"
    val textFieldDescription = "Search game"
    val supportingText = "You may search by game name"

    val contextFocus = LocalFocusManager.current

    val focusRequester = remember { FocusRequester() }
    val gamesState by viewModel.games.collectAsState()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = viewModel.gameToSearch.value,
                onValueChange = { viewModel.updateSearchTerm(it) },
                singleLine = true,
                placeholder = { Text(textHint) },
                label = { Text(textFieldDescription) },
                supportingText = { Text(supportingText) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        // TODO: Make filtering by name here, could also look for other filters
                        viewModel.searchByTitle(viewModel.gameToSearch.value)
                        contextFocus.clearFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                trailingIcon = {
                    if (viewModel.gameToSearch.value.isNotEmpty()) {
                        IconButton(onClick = { viewModel.updateSearchTerm("") }) {
                            Icon(
                                imageVector = Icons.Outlined.Clear,
                                contentDescription = null
                            )
                        }
                    }
                }
            )
        }

        LazyColumn {
            items(gamesState.games) { game ->
                ItemCard(game)
            }
        }
    }
}

@Composable
fun ElementList(list: List<Game>) {

    if (list.isNotEmpty()){
        Box(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)) {
            LazyColumn(modifier = Modifier){
                items(list) { item ->
                    ItemCard(gameModel = item)
                }
            }
        }
    }
}

@Composable
fun ItemCard(gameModel: Game) {
    Card(
        modifier = Modifier
            .padding(
                bottom = 9.dp,
                top = 9.dp,
                start = 4.dp,
                end = 4.dp
            )
            .fillMaxWidth(),
        shape = RoundedCornerShape(19.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colorScheme.surface)
                .padding(6.dp)
        ) {
            Surface(
                modifier = Modifier
                    .size(170.dp)
                    .padding(2.dp),
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 19.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                border = BorderStroke(1.dp, Color.Gray)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(gameModel.thumbnail),
                    contentDescription = gameModel.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .clip(shape = RoundedCornerShape(15.dp)),
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .align(Alignment.Top)
                    .fillMaxWidth()
            ) {
                Text(
                    text = gameModel.title,
                    fontWeight = FontWeight.Bold,
                    style = typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Genre: ${gameModel.genre}",
                    fontWeight = FontWeight.Normal,
                    style = typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Platform: ${gameModel.platform}",
                    fontWeight = FontWeight.Normal,
                    style = typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Developer: ${gameModel.developer}",
                    fontWeight = FontWeight.Normal,
                    style = typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "Publisher: ${gameModel.publisher}",
                    fontWeight = FontWeight.Normal,
                    style = typography.labelLarge,
                    color = MaterialTheme.colorScheme.tertiary
                )
                CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                    Text(
                        text = gameModel.description,
                        style = typography.bodyMedium,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(
                            end = 4.dp
                        ),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}