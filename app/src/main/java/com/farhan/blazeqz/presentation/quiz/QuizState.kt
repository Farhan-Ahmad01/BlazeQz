package com.farhan.blazeqz.presentation.quiz

import com.farhan.blazeqz.domain.model.QuizQuestion
import com.farhan.blazeqz.domain.model.UserAnswer

data class QuizState(
    val questions: List<QuizQuestion> = emptyList(),
    val answers: List<UserAnswer> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val isSubmitQuizDialogOpen: Boolean = false,
    val isExitQuizDialogOpen: Boolean = false,
    val loadingMessage: String? = null,
    val topBarTitle: String = "BlazeQz"
)
