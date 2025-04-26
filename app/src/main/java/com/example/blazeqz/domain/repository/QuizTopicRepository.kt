package com.example.blazeqz.domain.repository

import com.example.blazeqz.domain.model.QuizTopic
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

interface QuizTopicRepository {

    suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError>

    suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError>

}