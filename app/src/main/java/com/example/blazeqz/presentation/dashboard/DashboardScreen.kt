package com.example.blazeqz.presentation.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import com.example.blazeqz.R
import com.example.blazeqz.domain.model.QuizTopic
import com.example.blazeqz.presentation.common_component.ErrorScreen
import com.example.blazeqz.presentation.common_component.UserProgressCardLayout
import com.example.blazeqz.presentation.dashboard.components.NameEditDialog
import com.example.blazeqz.presentation.dashboard.components.ShimmerEffect
import com.example.blazeqz.presentation.dashboard.components.TopicCard
import com.example.blazeqz.presentation.dashboard.components.UserStatisticsCard

@Composable
fun DashboardScreen(
    state: DashboardState,
    onAction: (DashboardAction) -> Unit,
    onTopicCardClick: (Int) -> Unit
) {

    NameEditDialog(
        isOpen = state.isNameEditDialogOpen,
        textFieldValue = state.usernameTextFieldValue,
        usernameError = state.usernameError,
        onDialogDismiss = { onAction(DashboardAction.NameEditIconDismiss) },
        onConfirmButtonClick = {onAction(DashboardAction.NameEditDialogConfirm)},
        onTextFieldValueChange = { onAction(DashboardAction.SetUsername(it)) }
    )

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HeaderSection(
            modifier = Modifier.fillMaxWidth(),
            username = state.username,
            questionAttempted = state.questionAttempted,
//            totalQuestionsCount = state.allQuestionsCount,
            correctAnswer = state.correctAnswer,
            onEditNameClick = { onAction(DashboardAction.NameEditIconClick) }
        )

        QuizTopicSection(
            modifier = Modifier.fillMaxWidth(),
            quizTopics = state.quizTopics,
            isTopicsLoading = state.isLoading,
            errorMessage = state.errorMessage,
            onRefreshIconClicked = { onAction(DashboardAction.RefreshIconClick) },
            onTopicCardClick = onTopicCardClick
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HeaderSection(
    modifier: Modifier = Modifier,
    username: String,
    questionAttempted: Int,
    correctAnswer: Int,
    onEditNameClick: () -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.bodyMedium
            )
            Row {
                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineMedium
                )
                IconButton(
                    onClick = onEditNameClick
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_edit_24),
                        contentDescription = "edit name",
                        modifier = Modifier
                            .size(20.dp)
                            .offset(x = (-10).dp, y = (-10).dp)
                    )
                }
            }
        }
//        UserProgressCardLayout(
//            totalQuestions = totalQuestionsCount,
//            attemptedQuestionsCount = questionAttempted,
//            correctAnswersCount = correctAnswer
//        )
        UserStatisticsCard(
            modifier = Modifier
                .widthIn(max = 500.dp)
                .padding(10.dp),
            totalQuestions = 345,
            questionAttempted = questionAttempted,
            correctAnswers = correctAnswer
        )
    }
}

@Composable
private fun QuizTopicSection(
    modifier: Modifier = Modifier,
    quizTopics: List<QuizTopic>,
    isTopicsLoading: Boolean,
    errorMessage: String?,
    onRefreshIconClicked: () -> Unit,
    onTopicCardClick: (Int) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(10.dp),
            text = "Begin the Challenge",
            style = MaterialTheme.typography.titleLarge
        )

        if (errorMessage != null) {
            ErrorScreen(
                errorMessage = errorMessage,
                onRefreshIconClicked = onRefreshIconClicked
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 150.dp),
                contentPadding = PaddingValues(15.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                if (isTopicsLoading) {
                    items(count = 6) {
                        ShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp)
                                .clip(MaterialTheme.shapes.small)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                        )
                    }
                } else {
                    items(quizTopics) { topic ->
                        TopicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            topicName = topic.name,
                            imageUrl = topic.imageUrl,
                            onClick = { onTopicCardClick(topic.code) }
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewDashboardScreen() {
    val dummyTopics = List(size = 20) { index ->
        QuizTopic(id = "1", name = "Android $index", imageUrl = "", code = 0)
    }
    val state = DashboardState(
        questionAttempted = 10,
        correctAnswer = 7,
        quizTopics = dummyTopics,
        isLoading = false,
//        errorMessage = "something went off"
    )
    DashboardScreen(
        state = state,
        onAction = {},
        onTopicCardClick = {}
    )
}
