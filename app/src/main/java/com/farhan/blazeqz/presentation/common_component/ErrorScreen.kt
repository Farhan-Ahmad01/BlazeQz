package com.farhan.blazeqz.presentation.common_component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.farhan.blazeqz.R

@Composable
fun ErrorScreen(
    modifier: Modifier = Modifier,
    errorMessage: String,
    onRefreshIconClicked: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .clickable(
                    onClick = onRefreshIconClicked
                )
        ) {
            Row(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    onClick = onRefreshIconClicked
                ) {
                    Icon(
                        painter = painterResource(R.drawable.refresh),
                        contentDescription = "reload",
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier
                            .size(18.dp)
                            .rotate(90F)
                    )
                }

                Text(
                    text = "Reload",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 18.sp,
                    color = MaterialTheme.colorScheme.onPrimary
                )

            }
        }
        Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Text(
                text = errorMessage,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center
//                modifier = Modifier.align(Alignment.Center)
            )

    }

}

@Preview(showBackground = true)
@Composable
private fun ErrorScreenPreview() {
    ErrorScreen(
        errorMessage = "No Internet connection. check Your network",
        onRefreshIconClicked = {}
    )
}