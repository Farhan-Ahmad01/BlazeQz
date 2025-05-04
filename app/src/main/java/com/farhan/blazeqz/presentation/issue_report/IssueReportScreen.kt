package com.farhan.blazeqz.presentation.issue_report

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.farhan.blazeqz.presentation.issue_report.component.IssueReportScreenTopBar
import com.farhan.blazeqz.presentation.issue_report.component.QuestionCard
import kotlinx.coroutines.flow.Flow

@Composable
fun IssueReportScreen(
    state: IssueReportState,
    event: Flow<IssueReportEvent>,
    onAction: (IssueReportAction) -> Unit,
    navigateBack: () -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = Unit) {
        event.collect { event ->
            when (event) {
                is IssueReportEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_LONG).show()
                }

                IssueReportEvent.NavigateUp -> navigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            IssueReportScreenTopBar(
                title = "Report an Issue",
                onBackQuizButtonClick = navigateBack
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier
                        .padding(bottom = 10.dp),
                    onClick = {
                        onAction(IssueReportAction.SubmitReport)
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(),
                        text = "Submit Report"
                    )
                }

            }
        }
    ) { innerPading ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPading)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                QuestionCard(
                    modifier = Modifier.fillMaxWidth(),
                    question = state.quizQuestion,
                    isCardExpanded = state.isQuestionCardExpanded,
                    onExpandClick = { onAction(IssueReportAction.ExpandQuestionCard) }
                )
                Spacer(modifier = Modifier.height(15.dp))
                IssueTypeSection(
                    selectedIssue = state.issueType,
                    otherIssueText = state.otherIssueText,
                    onOtherIssueTextChange = { onAction(IssueReportAction.SetOtherIssueText(it)) },
                    onIssueTypeClick = {
                        onAction(IssueReportAction.SetIssueReportType(it))
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Surface(
                    shadowElevation = 8.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    TextField(
                        modifier = Modifier
                            .fillMaxSize(),
                        value = state.additionalComment,
                        onValueChange = {
                            onAction(IssueReportAction.SetAdditionalComment(it))
                        },
                        label = {
                            Text(
                                text = "Additional Comment",
                                style = MaterialTheme.typography.bodyMedium
                            )
                                },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                Surface(
                    shadowElevation = 5.dp,
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = state.emailForFollowUp,
                        onValueChange = {
                            onAction(IssueReportAction.SetEmailForFollowUp(it))
                        },
                        label = {
                            Text(
                                text = "Email for Follow-up (Optional)",
                                style = MaterialTheme.typography.bodyMedium
                            )
                                },
                        singleLine = true,
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        )
                    )
                }
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun IssueTypeSection(
    modifier: Modifier = Modifier,
    selectedIssue: IssueType,
    otherIssueText: String,
    onOtherIssueTextChange: (String) -> Unit,
    onIssueTypeClick: (IssueType) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "ISSUE TYPE",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        FlowRow {
            IssueType.entries.forEach { issue ->
                Row(
                    modifier = Modifier
                        .widthIn(250.dp)
                        .clickable { onIssueTypeClick(issue) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = issue == selectedIssue,
                        onClick = { onIssueTypeClick(issue) }
                    )
                    if (issue == IssueType.OTHER) {
                        Surface(
                            shadowElevation = 5.dp,
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.primaryContainer,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                modifier = Modifier
                                    .fillMaxSize(),
                                value = otherIssueText,
                                onValueChange = onOtherIssueTextChange,
                                placeholder = {
                                    Text(
                                        text = issue.text,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                singleLine = true,
                                enabled = selectedIssue == IssueType.OTHER,
                                colors = TextFieldDefaults.colors(
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent
                                )
                            )
                        }
                    } else {
                        Text(
                            text = issue.text,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}