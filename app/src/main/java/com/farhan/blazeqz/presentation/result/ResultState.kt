package com.farhan.blazeqz.presentation.result

import com.farhan.blazeqz.domain.model.QuizQuestion
import com.farhan.blazeqz.domain.model.UserAnswer

data class ResultState(
    val scorePercentage: Int = 0,
    val correctAnswerCount: Int = 0,
    val totalQuestions: Int = 0,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val userAnswer: List<UserAnswer> = emptyList()
)
