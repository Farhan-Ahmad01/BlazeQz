package com.farhan.blazeqz.domain.repository

import com.farhan.blazeqz.domain.model.QuizTopic
import com.farhan.blazeqz.domain.util.DataError
import com.farhan.blazeqz.domain.util.Result

interface QuizTopicRepository {

    suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError>

    suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError>

}