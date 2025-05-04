package com.farhan.blazeqz.data.mapper

import com.farhan.blazeqz.data.local.entity.QuestionsCountEntity
import com.farhan.blazeqz.data.remote.dto.QuestionsCountDto
import com.farhan.blazeqz.domain.model.QuestionsCount

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