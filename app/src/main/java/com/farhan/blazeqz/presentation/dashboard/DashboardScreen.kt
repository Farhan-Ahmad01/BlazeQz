package com.farhan.blazeqz.presentation.dashboard

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
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.farhan.blazeqz.R
import com.farhan.blazeqz.domain.model.QuizTopic
import com.farhan.blazeqz.presentation.common_component.ErrorScreen
import com.farhan.blazeqz.presentation.dashboard.components.CustomTopicCard
import com.farhan.blazeqz.presentation.dashboard.components.NameEditDialog
import com.farhan.blazeqz.presentation.dashboard.components.ShimmerEffect
import com.farhan.blazeqz.presentation.dashboard.components.UserStatisticsCard

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
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        HeaderSection(
            modifier = Modifier.fillMaxWidth(),
            username = state.username,
            questionAttempted = state.questionAttempted,
            correctAnswer = state.correctAnswer,
            totalQuestions = state.allQuestionsCount,
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
    totalQuestions: Int,
    onEditNameClick: () -> Unit
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .padding(top = 50.dp, start = 10.dp, end = 10.dp)
        ) {
            Text(
                text = "Hello",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row {
                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(
                    onClick = onEditNameClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
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
        UserStatisticsCard(
            modifier = Modifier
                .widthIn(max = 500.dp)
                .padding(10.dp),
            totalQuestions = totalQuestions,
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
    val possibleHeights = listOf( 260, 200, 187,133, 180, 273, 190, 120, 155, 245, 100, 205, 149, 235, 240, 168, 140, 253, 220, 210, 110, 160
    )
    val topicHeights = rememberSaveable(quizTopics) {
        quizTopics.associate { topic ->
            topic.code to possibleHeights.random()
        }
    }
    val gridState = rememberLazyStaggeredGridState()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (errorMessage != null) {
            ErrorScreen(
                errorMessage = errorMessage,
                onRefreshIconClicked = onRefreshIconClicked
            )
        } else {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp),
                state = gridState,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalItemSpacing = 16.dp
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
                    items(
                        items = quizTopics,
                        key = { topic ->
                            topic.id
                        }
                    ) { topic ->
                        val height = topicHeights[topic.code]?.dp ?: 150.dp
                        CustomTopicCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(height),
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
