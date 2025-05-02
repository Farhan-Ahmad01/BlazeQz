package com.example.blazeqz.presentation.quiz

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.blazeqz.domain.model.QuizQuestion
import com.example.blazeqz.domain.model.UserAnswer
import com.example.blazeqz.presentation.common_component.ErrorScreen
import com.example.blazeqz.presentation.quiz.component.ExitQuizDialog
import com.example.blazeqz.presentation.quiz.component.QuizScreenLoadingContent
import com.example.blazeqz.presentation.quiz.component.QuizSubmitButtons
import com.example.blazeqz.presentation.quiz.component.SubmitQuizDialog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    state: QuizState,
    event: Flow<QuizEvent>,
    navigationToDashboardScreen: () -> Unit,
    navigationToResultScreen: () -> Unit,
    onAction: (QuizAction) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            when (event) {
                is QuizEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                QuizEvent.NavigateToDashboardScreen -> navigationToDashboardScreen()
                QuizEvent.NavigateToResultScreen -> navigationToResultScreen()
            }
        }
    }

    SubmitQuizDialog(
        isOpen = state.isSubmitQuizDialogOpen,
        onDialogDismiss = { onAction(QuizAction.SubmitQuizDialogDismiss) },
        onConfirmButtonClick = { onAction(QuizAction.SubmitQuizConfirmButtonClick) }
    )

    ExitQuizDialog(
        isOpen = state.isExitQuizDialogOpen,
        onDialogDismiss = { onAction(QuizAction.ExitQuizDialogDismiss) },
        onConfirmButtonClick = { onAction(QuizAction.ExitQuizConfirmButtonClick) }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.topBarTitle,
                    )
                },
                actions = {
                    IconButton(
                        onClick = { onAction(QuizAction.ExitQuizButtonClick) }
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
        },
        bottomBar = {
            QuizSubmitButtons(
                modifier = Modifier
                    .fillMaxWidth(),
                isPreviousButtonEnabled = state.currentQuestionIndex != 0,
                isNextButtonEnabled = state.currentQuestionIndex != state.questions.lastIndex,
                onPreviousButtonClicked = { onAction(QuizAction.PreviousButtonClick) },
                onNextButtonClicked = { onAction(QuizAction.NextButtonClick) },
                onSubmitButtonClicked = { onAction(QuizAction.SubmitQuizButtonClick) }
            )
        }
    ) { innerPading ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPading)
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (state.isLoading) {
                QuizScreenLoadingContent(
                    modifier = Modifier.fillMaxSize(),
                    loadingMessage = state.loadingMessage
                )
            } else {
                when {
                    state.errorMessage != null -> {
                        ErrorScreen(
                            modifier = Modifier.fillMaxSize(),
                            errorMessage = state.errorMessage,
                            onRefreshIconClicked = { onAction(QuizAction.Refresh) },
                        )
                    }

                    state.questions.isEmpty() -> {
                        ErrorScreen(
                            modifier = Modifier.fillMaxSize(),
                            errorMessage = "No Quiz Question Available",
                            onRefreshIconClicked = { onAction(QuizAction.Refresh) },
                        )
                    }

                    else -> {
                        QuizScreenContent(
                            state = state,
                            onAction = onAction,
                            onSubmitButtonClicked = navigationToResultScreen
                        )
                    }
                }
            }


        }


    }
}

@Composable
private fun QuizScreenContent(
    modifier: Modifier = Modifier,
    state: QuizState,
    onAction: (QuizAction) -> Unit,
    onSubmitButtonClicked: () -> Unit
) {

    val pagerState = rememberPagerState(
        pageCount = { state.questions.size }
    )

    LaunchedEffect(key1 = pagerState) {
        snapshotFlow { pagerState.settledPage }.collect { pageIndex ->
            onAction(QuizAction.JumpToQuestion(pageIndex))
        }
    }

    LaunchedEffect(key1 = state.currentQuestionIndex) {
        pagerState.animateScrollToPage(state.currentQuestionIndex)
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        QuestionNavigationRow(
            currentQuestionIndex = state.currentQuestionIndex,
            questions = state.questions,
            answers = state.answers,
            onTabSelected = { index ->
                onAction(QuizAction.JumpToQuestion(index))
            }
        )
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .weight(1f)
        ) {
            QuestionItem(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
                    .verticalScroll(rememberScrollState()),
                currentQuestionIndex = state.currentQuestionIndex,
                questions = state.questions,
                answers = state.answers,
                onOptionSelected = { questionId, option ->
                    onAction(QuizAction.OnOptionSelected(questionId, option))
                }
            )
        }
    }
}

@Composable
private fun QuestionNavigationRow(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    questions: List<QuizQuestion>,
    answers: List<UserAnswer>,
    onTabSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        modifier = modifier,
        selectedTabIndex = currentQuestionIndex,
        edgePadding = 0.dp,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[currentQuestionIndex]),
                color = MaterialTheme.colorScheme.primary
            )
        }
    ) {
        questions.forEachIndexed { index, question ->
            val isAnswered = answers.any() { it.questionId == question.id }
            val isSelected = currentQuestionIndex == index

            // new
            val containerColor = when {
                isSelected -> MaterialTheme.colorScheme.primaryContainer
                isAnswered -> MaterialTheme.colorScheme.primaryContainer
                else -> MaterialTheme.colorScheme.surface
            }

            // Text color based on state
            val textColor = when {
                isSelected -> MaterialTheme.colorScheme.onPrimaryContainer
                isAnswered -> MaterialTheme.colorScheme.onTertiaryContainer
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            }

            Tab(
                modifier = Modifier.background(containerColor),
                selected = currentQuestionIndex == index,
                onClick = { onTabSelected(index) },
                selectedContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = "${index + 1}",
                    color = textColor
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun QuestionItem(
    modifier: Modifier = Modifier,
    currentQuestionIndex: Int,
    questions: List<QuizQuestion>,
    answers: List<UserAnswer>,
    onOptionSelected: (String, String) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        val currentQuestion = questions[currentQuestionIndex]
        val selectedAnswer = answers.find { it.questionId == currentQuestion.id }?.selectedOption
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondaryContainer)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentQuestion.question,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(10.dp))

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            currentQuestion.allOptions.forEach { option ->
                OptionItem(
                    modifier = Modifier
                        .widthIn(min = 400.dp)
                        .padding(vertical = 10.dp),
                    option = option,
                    isSelected = option == selectedAnswer,
                    onClick = { onOptionSelected(currentQuestion.id, option) }
                )
            }
        }

    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    option: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .clickable { onClick() }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = MaterialTheme.shapes.small
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) {
                MaterialTheme.colorScheme.surfaceContainerHighest
            } else MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Row(
            modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonColors(
                    selectedColor = MaterialTheme.colorScheme.secondary,
                    unselectedColor = MaterialTheme.colorScheme.outline,
                    disabledSelectedColor = MaterialTheme.colorScheme.outlineVariant,
                    disabledUnselectedColor = MaterialTheme.colorScheme.outlineVariant
                )
            )

            Text(
                text = option,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


//@Preview(showBackground = true)
@Preview(showBackground = true)
@Composable
private fun PreviewQuizScreen() {
    val dummyQuestions = List(size = 10) { index ->
        QuizQuestion(
            id = "${index}",
            topicCode = 1,
            question = "What is the purpose of Infrastructure as Code (IaC) tools like Terraform or AWS CloudFormation?",
            allOptions = listOf(
                "To Perform the health and performance of infrastructure resources in real-time",
                "To provide a graphical interface for managing cloud resources.",
                "To automatically generate application code from infrastructure configuration",
                "To define and manage infrastructure resources (e.g., servers, databases, networks_ using code, enabling automation, version control, and repeataility"
            ),
            correctAnswer = "Kotlin",
            explanation = "By Google"
        )
    }
    val dummyAnswers = listOf(
        UserAnswer(questionId = "1", selectedOption = ""),
        UserAnswer(questionId = "3", selectedOption = ""),
    )
    QuizScreen(
        state = QuizState(
            questions = dummyQuestions,
            answers = dummyAnswers,
        ),
        navigationToResultScreen = {},
        navigationToDashboardScreen = {},
        onAction = {},
        event = emptyFlow()
    )
}