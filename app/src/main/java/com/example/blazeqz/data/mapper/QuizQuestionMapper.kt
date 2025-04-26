package com.example.blazeqz.data.mapper

import com.example.blazeqz.data.local.entity.QuizQuestionEntity
import com.example.blazeqz.data.remote.dto.QuizQuestionDto
import com.example.blazeqz.domain.model.QuizQuestion

private fun QuizQuestionDto.toQuizQuestion() = QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    correctAnswer = correctAnswer,
    explanation = explanation
)

private fun QuizQuestionDto.toQuizQuestionEntity() = QuizQuestionEntity(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation
)

fun QuizQuestionEntity.entityToQuizQuestion() = QuizQuestion(
    id = id,
    topicCode = topicCode,
    question = question,
    correctAnswer = correctAnswer,
    allOptions = (incorrectAnswers + correctAnswer).shuffled(),
    explanation = explanation
)

fun List<QuizQuestionDto>.toQuizQuestions() = map { it.toQuizQuestion() }

fun List<QuizQuestionDto>.toQuizQuestionsEntity() = map { it.toQuizQuestionEntity() }

fun List<QuizQuestionEntity>.entityToQuizQuestions() = map { it.entityToQuizQuestion() }


