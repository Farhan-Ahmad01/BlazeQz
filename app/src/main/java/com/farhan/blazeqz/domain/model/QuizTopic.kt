package com.farhan.blazeqz.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class QuizTopic(
    val id: String,
    val name: String,
    val imageUrl: String,
    val code: Int
)
