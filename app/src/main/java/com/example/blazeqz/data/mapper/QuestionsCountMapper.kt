package com.example.blazeqz.data.mapper

import com.example.blazeqz.data.local.entity.QuestionsCountEntity
import com.example.blazeqz.data.remote.dto.QuestionsCountDto
import com.example.blazeqz.domain.model.QuestionsCount

fun QuestionsCountDto.toQuestionsCount() = QuestionsCount(
    id = id,
    count = count
)

fun QuestionsCountDto.toQuestionCountEntity() = QuestionsCountEntity(
    id = id,
    count = count
)

fun QuestionsCountEntity.entityToQuestionsCount() = QuestionsCount(
    id = id,
    count = count
)