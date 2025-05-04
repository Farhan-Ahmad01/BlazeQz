package com.farhan.blazeqz.presentation.quiz

sealed interface QuizEvent {
    data class ShowToast(val message: String): QuizEvent
    data object NavigateToResultScreen: QuizEvent
    data object NavigateToDashboardScreen: QuizEvent
}