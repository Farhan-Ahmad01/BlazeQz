package com.example.blazeqz.presentation.result

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.checkScrollableContainerConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blazeqz.R
import com.example.blazeqz.domain.model.QuizQuestion
import com.example.blazeqz.domain.model.UserAnswer
import com.example.blazeqz.presentation.common_component.CustomCircularProgressIndicator
import com.example.blazeqz.presentation.common_component.UserProgressCardLayout
import com.example.blazeqz.presentation.quiz.QuizEvent
import com.example.blazeqz.presentation.theme.CustomGreen
import com.example.blazeqz.presentation.theme.gray
import com.example.blazeqz.presentation.theme.orange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.koin.core.component.getScopeId

@Composable
fun ResultScreen(
    state: ResultState,
    event: Flow<ResultEvent>,
    onReportIconClick: (String) -> Unit,
    onStartNewQuiz: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            when (event) {
                is ResultEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
            item {
                ScoreCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 80.dp, horizontal = 10.dp),
                    scorePercentage = state.scorePercentage,
                    correctAnswerCount = state.correctAnswerCount,
                    totalQuestions = state.totalQuestions
                )
            }
            item {
                Text(
                    text = "Quiz Questions",
                    style = MaterialTheme.typography.titleLarge,
                    textDecoration = TextDecoration.Underline
                )
            }
            items(state.quizQuestions) { question ->
                val userAnswer = state.userAnswer
                    .find { it.questionId == question.id }?.selectedOption
                QuestionItem(
                    question = question,
                    userSelectedAnswer = userAnswer,
                    onReportIconClick = { onReportIconClick(question.id) }
                )
            }
        }
        Button(
            modifier = Modifier.padding(10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = onStartNewQuiz
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 10.dp),
                text = "Start New Quiz"
            )
        }
    }

}

@Composable
private fun ScoreCard(
    modifier: Modifier = Modifier,
    scorePercentage: Int,
    correctAnswerCount: Int,
    totalQuestions: Int
) {
    val resultText = when (scorePercentage) {
        in 71..100 -> "Congratulations!\n A great performance!"
        in 41..70 -> "You did well,\n but there's room for improvement"
        else -> "You may have struggled this time.\n Mistakes are part of learning, keep going!"
    }
    val resultIconResId = when (scorePercentage) {
        in 71..100 -> R.drawable.ic_laugh
        in 41..70 -> R.drawable.ic_smiley
        else -> R.drawable.ic_sad
    }

    val initialValue = (correctAnswerCount * 100) / 10

    Card(
        modifier = modifier
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(20.dp)
                .size(100.dp),
            painter = painterResource(resultIconResId),
            contentDescription = "Score Emoji"
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = "You answered correctly $correctAnswerCount out of $totalQuestions.",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            text = resultText,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    question: QuizQuestion,
    userSelectedAnswer: String?,
    onReportIconClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Q : ${question.question}",
                fontWeight = FontWeight.Bold
            )
            IconButton(
                onClick = onReportIconClick
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Report"
                )
            }
        }

        question.allOptions.forEachIndexed { index, option ->
            val letter = when (index) {
                0 -> "A : "
                1 -> "B : "
                2 -> "C : "
                3 -> "D : "
                else -> ""
            }
            val optionColor = when (option) {
                question.correctAnswer -> CustomGreen
                userSelectedAnswer -> MaterialTheme.colorScheme.error
                else -> LocalContentColor.current
            }
            Text(
                text = letter + option,
                color = optionColor
            )
        }
        Text(
            modifier = Modifier.padding(vertical = 10.dp),
            text = question.explanation,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        HorizontalDivider()
    }
}

@Preview(showBackground = true)
@Composable
private fun ResultScreenPreview() {
    val dummyQuestions = List(size = 10) { index ->
        QuizQuestion(
            id = "$index",
            topicCode = 1,
            question = "What is the language for Android Dev?",
            allOptions = listOf("Java", "Python", "Dart", "Kotlin"),
            correctAnswer = "Kotlin",
            explanation = "Some Explanation"
        )
    }
    val dummyAnswers = listOf(
        UserAnswer(questionId = "1", selectedOption = "Python"),
        UserAnswer(questionId = "0", selectedOption = "Kotlin"),
    )
    ResultScreen(
        state = ResultState(
            scorePercentage = 71,
            correctAnswerCount = 71,
            totalQuestions = 100,
            quizQuestions = dummyQuestions,
            userAnswer = dummyAnswers
        ),
        onReportIconClick = {},
        onStartNewQuiz = {},
        event = emptyFlow()
    )
}