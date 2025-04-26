package com.example.blazeqz.presentation.result

import com.example.blazeqz.domain.model.QuizQuestion
import com.example.blazeqz.domain.model.UserAnswer

data class ResultState(
    val scorePercentage: Int = 0,
    val correctAnswerCount: Int = 0,
    val totalQuestions: Int = 0,
    val quizQuestions: List<QuizQuestion> = emptyList(),
    val userAnswer: List<UserAnswer> = emptyList()
)
