package com.example.blazeqz.presentation.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blazeqz.R
import com.example.blazeqz.presentation.theme.CustomBlue
import com.example.blazeqz.presentation.theme.CustomPink

@Composable
fun UserStatisticsCard(
    modifier: Modifier = Modifier,
    questionAttempted: Int,
    correctAnswers: Int,
    totalQuestions: Int
) {
    val barProgress = if (correctAnswers > 0) {
         correctAnswers.toFloat() / totalQuestions
    } else 0f

    Card(
        modifier = modifier
    ) {
        ProgressBar(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .height(15.dp),
            barProgress = barProgress
        )
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Statistics(
                value = totalQuestions,
                description = "Total",
                iconResId = R.drawable.all_questions
            )

            Statistics(
                value = questionAttempted,
                description = "Attempted",
                iconResId = R.drawable.attempt_questions
            )

            Statistics(
                value = correctAnswers,
                description = "Correct",
                iconResId = R.drawable.questions_done
            )

        }
    }

}

@Composable
fun ProgressBar(
    modifier: Modifier = Modifier,
    barProgress: Float,
    gradientColor: List<Color> = listOf(CustomPink, CustomBlue)
) {
    Box(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .background(Color.White)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(barProgress)
                .fillMaxHeight()
                .clip(MaterialTheme.shapes.extraSmall)
                .background(Brush.linearGradient(gradientColor))
        )

        Box(
            modifier = Modifier
                .padding(end = 5.dp)
                .align(Alignment.CenterEnd)
                .size(5.dp)
                .clip(CircleShape)
                .background(Brush.linearGradient(gradientColor))
        )
    }
}

@Composable
private fun Statistics(
    modifier: Modifier = Modifier,
    value: Int,
    description: String,
    iconResId: Int
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(35.dp)
                .clip(MaterialTheme.shapes.small)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(iconResId),
                contentDescription = description,
                tint = Color.Black,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(
            modifier = Modifier.width(10.dp)
        )
        Column {
            Text(
                text = "$value",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall
            )
        }
    }
}


@Preview
@Composable
private fun StatisticsPreview() {
    UserStatisticsCard(
        totalQuestions = 100,
        questionAttempted = 60,
        correctAnswers = 50
    )
}