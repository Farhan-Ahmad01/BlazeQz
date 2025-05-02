package com.example.blazeqz.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.blazeqz.presentation.dashboard.DashboardScreen
import com.example.blazeqz.presentation.dashboard.DashboardViewModel
import com.example.blazeqz.presentation.issue_report.IssueReportScreen
import com.example.blazeqz.presentation.issue_report.IssueReportViewModel
import com.example.blazeqz.presentation.preview.PreviewScreen
import com.example.blazeqz.presentation.quiz.QuizScreen
import com.example.blazeqz.presentation.quiz.QuizViewModel
import com.example.blazeqz.presentation.result.ResultScreen
import com.example.blazeqz.presentation.result.ResultViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun NavGraph(
    navController: NavHostController,
    ) {

    NavHost(
        modifier = Modifier,
        navController = navController,
        startDestination = Route.DashboardScreen
    ) {
        composable<Route.DashboardScreen> {
            val viewModel = koinViewModel<DashboardViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            DashboardScreen(
                state = state,
                onAction = viewModel::onAction,
                onTopicCardClick = { topicCode ->
                    navController.navigate(Route.QuizScreen(topicCode))
                }
            )
        }
        composable<Route.QuizScreen> {

            val viewModel = koinViewModel<QuizViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            QuizScreen(
                state = state,
                event = viewModel.event,
                onAction = viewModel::onAction,
                navigationToResultScreen = {
                    navController.navigate(Route.PreviewScreen) {
                        popUpTo<Route.QuizScreen> {  inclusive = true }
                    }
                },
                navigationToDashboardScreen = {
                    navController.navigateUp()
                }
            )
        }
        composable<Route.ResultScreen> {
            val viewModel = koinViewModel<ResultViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            ResultScreen(
                state = state,
                event = viewModel.event,
                onReportIconClick = { questionId ->
                    navController.navigate(Route.IssueReportScreen(questionId))
                },
                onStartNewQuiz = {
                    navController.navigate(Route.DashboardScreen) {
                        popUpTo<Route.ResultScreen> {  inclusive = true }
                    }
                }
            )
        }
        composable<Route.IssueReportScreen> {
            val viewModel = koinViewModel<IssueReportViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()
            IssueReportScreen(
                state = state,
                event = viewModel.event,
                onAction = viewModel::onAction,
                navigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable<Route.PreviewScreen> {
            val resultViewModel = koinViewModel<ResultViewModel>()
            val resultState by resultViewModel.state.collectAsStateWithLifecycle()
            PreviewScreen(
                correctAnswerCount = resultState.correctAnswerCount,
                onPreviewClick = {
                    navController.navigate(Route.ResultScreen) {
                        popUpTo<Route.PreviewScreen> {  inclusive = true }
                    }
                },
                onNewQuizClick = {
                    navController.navigate(Route.DashboardScreen) {
                        popUpTo<Route.PreviewScreen> {inclusive = true}
                    }
                }
            )
        }

    }

}