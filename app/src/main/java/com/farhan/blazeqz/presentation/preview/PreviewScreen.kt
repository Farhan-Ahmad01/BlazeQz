package com.farhan.blazeqz.presentation.preview

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.farhan.blazeqz.R

@Composable
fun PreviewScreen(
    correctAnswerCount: Int,
    onPreviewClick: () -> Unit,
    onNewQuizClick: () -> Unit
) {

    val composition by rememberLottieComposition(
        spec = LottieCompositionSpec.RawRes(R.raw.congrats)
    )

    val answerText = "You got ${correctAnswerCount}/10 Questions correct"

    val resultTextHeadline = when (correctAnswerCount) {
        in 9..10 -> "Outstanding !"
        in 7..8 -> "Nice Work !"
        in 4..6 -> "Well Tried !"
        else -> "Don't Give Up !"
    }

    val resultText = when (correctAnswerCount) {
        in 9..10 -> "Keep up the great streak"
        in 7..8-> "You are doing Good, Keep it up"
        in 4..6 -> "You’re improving steadily"
        else -> "Growth takes time. Keep at it"
    }

    val resultIconResId = when (correctAnswerCount) {
        in 7..8-> R.drawable.goodjob
        in 4..6 -> R.drawable.can_be_better
        else -> R.drawable.cheerupxml
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (correctAnswerCount == 9 || correctAnswerCount == 10) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            modifier = Modifier.size(300.dp)
        )
        } else {
            Image(
                painter = painterResource(resultIconResId),
                contentDescription = null,
                modifier = Modifier.size(200.dp)
                    .offset(y = (-40).dp)
            )
        }

        Text(
            text = resultTextHeadline,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.offset(y = (-30).dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = answerText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.offset(y = (-25).dp),
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = resultText,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.offset(y = (-25).dp),
            color = MaterialTheme.colorScheme.onBackground
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 50.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    onPreviewClick()
                },
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(0.5f)
                )
            ) {
                Text(
                    text = "Preview",
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(
                onClick = {
                    onNewQuizClick()
                },
                modifier = Modifier.fillMaxWidth()
                    .weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "New Quiz",
                    modifier = Modifier.padding(vertical = 5.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}