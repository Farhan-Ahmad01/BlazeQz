package com.farhan.blazeqz.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade

@Composable
fun CustomTopicCard(
    modifier: Modifier = Modifier,
    topicName: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val imageRequest = ImageRequest
        .Builder(context)
        .data(imageUrl)
        .crossfade(enable = true)
        .build()
    Card(
        modifier = modifier
            .background(Color.Transparent)
            .clip(RoundedCornerShape(15.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
                .clip(RoundedCornerShape(15.dp)),
            contentAlignment = Alignment.Center
        ) {

            AsyncImage(
                modifier = modifier.fillMaxSize(),
                model = imageRequest,
                contentDescription = null,
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier
                    .height(35.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd)
                    .background(Color.Black.copy(0.6f)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = topicName,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White
                )
            }
        }
    }
}