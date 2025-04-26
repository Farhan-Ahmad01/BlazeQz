package com.example.blazeqz.data.mapper

import com.example.blazeqz.data.local.entity.UserAnswerEntity
import com.example.blazeqz.domain.model.UserAnswer

private fun UserAnswer.toUserAnswerEntity() = UserAnswerEntity(
    questionId = questionId,
    selectedOption = selectedOption
)

private fun UserAnswerEntity.toUserAnswer() = UserAnswer(
    questionId = questionId,
    selectedOption = selectedOption
)

fun List<UserAnswerEntity>.toUserAnswers() = map { it.toUserAnswer() }

fun List<UserAnswer>.toUserAnswersEntity() = map { it.toUserAnswerEntity() }