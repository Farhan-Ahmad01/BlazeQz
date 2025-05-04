package com.farhan.blazeqz.presentation.dashboard

import com.farhan.blazeqz.domain.model.QuizTopic

data class DashboardState(
    val username: String = "404 Brain Not Found",
    val questionAttempted: Int = 0,
    val correctAnswer: Int = 0,
    val allQuestionsCount: Int = 253,
    val quizTopics: List<QuizTopic> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isNameEditDialogOpen: Boolean = false,
    val usernameTextFieldValue: String = "",
    val usernameError: String? = null
)
