package com.farhan.blazeqz.presentation.result

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.farhan.blazeqz.domain.model.QuizQuestion
import com.farhan.blazeqz.presentation.theme.CustomGreen
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
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

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(
                    text = "PREVIEW",
                ) },
                actions = {
                    IconButton(
                        onClick = onStartNewQuiz
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Exit Quiz"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground,
                    actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                )
            )
        }
    ){ innerPadding ->
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(10.dp)
        ) {
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
            modifier = Modifier.padding(vertical = 10.dp)
                .align(Alignment.CenterHorizontally),
            onClick = onStartNewQuiz
        ) {
            Text(
                modifier = Modifier.padding(vertical = 5.dp),
                text = "New Quiz"
            )
        }
    }
    }

}

@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    question: QuizQuestion,
    userSelectedAnswer: String?,
    onReportIconClick: () -> Unit
) {
    Surface(
        shadowElevation = 5.dp,
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceContainerHighest,
    ) {
        Column(modifier = Modifier.padding(5.dp)) {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Q : ${question.question}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            IconButton(
                onClick = onReportIconClick,
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Report"
                )
            }
        }

        question.allOptions.forEachIndexed { index, option ->
            val letter = when (index) {
                0 -> "A  "
                1 -> "B  "
                2 -> "C  "
                3 -> "D  "
                else -> ""
            }
            val optionColor = when (option) {
                question.correctAnswer -> CustomGreen
                userSelectedAnswer -> MaterialTheme.colorScheme.error
                else -> MaterialTheme.colorScheme.onBackground


            }
            Text(
                text = letter + option,
                color = optionColor,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.height(3.dp))
        }
        Text(
            modifier = Modifier.padding(vertical = 7.dp),
            text = question.explanation,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
            style = MaterialTheme.typography.labelMedium
        )
        }
    }
    Spacer(modifier = Modifier.height(10.dp))
}