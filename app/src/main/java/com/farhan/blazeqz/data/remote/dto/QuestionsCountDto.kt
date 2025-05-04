package com.farhan.blazeqz.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class QuestionsCountDto(
    val id: String,
    val count: Int
)
