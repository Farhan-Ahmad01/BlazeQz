package com.example.blazeqz.data.mapper

import com.example.blazeqz.data.local.entity.QuizTopicEntity
import com.example.blazeqz.data.remote.dto.QuizTopicDto
import com.example.blazeqz.data.util.Constant.BASE_URL
import com.example.blazeqz.domain.model.QuizTopic

private fun QuizTopicDto.toQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)

private fun QuizTopicDto.toQuizTopicEntity() = QuizTopicEntity(
    id = id,
    name = name,
    imageUrl = BASE_URL + imageUrl,
    code = code
)

fun QuizTopicEntity.entityToQuizTopic() = QuizTopic(
    id = id,
    name = name,
    imageUrl = imageUrl,
    code = code
)

fun List<QuizTopicDto>.toQuizTopics() = map { it.toQuizTopic() }

fun List<QuizTopicDto>.toQuizTopicsEntity() = map { it.toQuizTopicEntity() }

fun List<QuizTopicEntity>.entityToQuizTopics() = map { it.entityToQuizTopic() }