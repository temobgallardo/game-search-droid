package com.challenge.gamesearch.ui

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ContentAlpha
import androidx.wear.compose.material.LocalContentAlpha
import coil.compose.rememberAsyncImagePainter
import com.challenge.gamesearch.domain.models.Game
import com.challenge.gamesearch.ui.viewmodels.GameListEvent
import com.challenge.gamesearch.ui.viewmodels.GameSearchViewModel

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun GameSearchView(
    modifier: Modifier = Modifier,
    viewModel: GameSearchViewModel = hiltViewModel(),
    navigateToGameDetails: (gameId: String) -> Unit
) {
    val textHint = "Type a game to filter the games"
    val textFieldDescription = "Search game"
    val supportingText = "You may search by game name"

    val focusRequester = remember { FocusRequester() }
    val gamesState by viewModel.gamesUiStates.collectAsState()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(value = gamesState.toSearch,
                onValueChange = { viewModel.onEvent(GameListEvent.FilterByName(it)) },
                singleLine = true,
                placeholder = { Text(textHint) },
                label = { Text(textFieldDescription) },
                supportingText = { Text(supportingText) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                trailingIcon = {
                    if (gamesState.toSearch.isNotEmpty()) {
                        IconButton(onClick = {
                            viewModel.onEvent(GameListEvent.FilterByName(""))
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Clear, contentDescription = null
                            )
                        }
                    }
                })
        }

        TabsForMainFilters(onClick = {
            Log.d("INFO", "Category: $it")
            viewModel.onEvent(GameListEvent.SetCategory(it))
        }, gamesState.categories.keys.toList())

        AnimatedVisibility(visible = gamesState.subCategories.isNotEmpty()) {
            SubCategoryFilerChipGroup(
                modifier = Modifier.padding(start = 8.dp, end = 12.dp),
                chips = gamesState.subCategories,
                currentSelected = gamesState.subCategorySelected,
                itemSelected = { select ->
                    Log.d("INFO", "Subcategory selected: $select")
                    viewModel.onEvent(GameListEvent.FilterBySubCategory(select))
                }
            )
        }

        ElementList(list = gamesState.games, navigateToGameDetails)
    }
}

@Composable
fun TabsForMainFilters(onClick: (String) -> Unit, tabs: List<String>) {
    val selectedIndex = remember { mutableIntStateOf(0) }

    if (tabs.isEmpty()) return

    ScrollableTabRow(selectedTabIndex = selectedIndex.intValue,
        containerColor = Color.Transparent,
        contentColor = Color(0xFFFEFEFA),
        indicator = {
            Spacer(
                Modifier
                    .tabIndicatorOffset(it[selectedIndex.intValue])
                    .height(2.5.dp)
                    .background(Color.Black)
            )
        }) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                selected = selectedIndex.intValue == index,
                onClick = {
                    onClick.invoke(tabs[index])
                },
            ) {
                Text(
                    text = tab,
                    modifier = Modifier.padding(12.dp),
                )
            }
        }
    }

}

@Composable
fun SubCategoryFilerChip(
    modifier: Modifier = Modifier,
    subCategory: String,
    selected: Boolean = false,
    onClick: () -> Unit
) {
    FilterChip(modifier = modifier,
        selected = selected,
        onClick = { onClick() },
        label = { Text(text = subCategory, style = MaterialTheme.typography.bodySmall) },
        leadingIcon = if (selected) {
            {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize),
                )
            }
        } else {
            null
        })
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SubCategoryFilerChipGroup(
    modifier: Modifier = Modifier,
    chips: List<String>,
    currentSelected: String?,
    itemSelected: (String?) -> Unit
) {
    Log.d("INFO", "SubCategory Filter Chips, Current Selected: $currentSelected")
    FlowRow(
        maxItemsInEachRow = 10,
        modifier = modifier.padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        maxLines = 2
    ) {
        chips.forEach { item ->
            SubCategoryFilerChip(subCategory = item, selected = currentSelected == item) {
                if (currentSelected != item) {
                    itemSelected(item)
                } else itemSelected(null)
            }
        }
    }
}

@Composable
fun ElementList(list: List<Game>, navigateToGameDetails: (gameId: String) -> Unit) {
    if (list.isEmpty()) return

    Box(
        Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list) { item ->
                ItemCard(gameModel = item, {
                    navigateToGameDetails(item.id)
                })
            }
        }
    }
}

@Composable
fun ItemCard(gameModel: Game, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(
                bottom = 9.dp, top = 9.dp, start = 4.dp, end = 4.dp
            )
            .fillMaxWidth(), shape = RoundedCornerShape(19.dp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary,
            contentColor = MaterialTheme.colorScheme.onTertiary
        ), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        onClick = onClick
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