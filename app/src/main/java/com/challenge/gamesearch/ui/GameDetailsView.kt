package com.challenge.gamesearch.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.challenge.gamesearch.ui.viewmodels.StateGameDetails

@Composable
fun GameDetailsView(modifier: Modifier = Modifier, gameDetailsStates: StateGameDetails) {

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberAsyncImagePainter(gameDetailsStates.game?.thumbnail),
            contentDescription = gameDetailsStates.game?.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(shape = RoundedCornerShape(15.dp)),
            contentScale = ContentScale.Crop,
        )
        Text(text = gameDetailsStates.game?.title ?: "")
    }
}
