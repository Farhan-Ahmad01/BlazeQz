package com.example.blazeqz.data.repository

import com.example.blazeqz.data.local.dao.QuizTopicDao
import com.example.blazeqz.data.mapper.entityToQuizTopic
import com.example.blazeqz.data.mapper.entityToQuizTopics
import com.example.blazeqz.data.mapper.toQuizTopics
import com.example.blazeqz.data.mapper.toQuizTopicsEntity
import com.example.blazeqz.data.remote.RemoteQuizDataSource
import com.example.blazeqz.domain.model.QuizTopic
import com.example.blazeqz.domain.repository.QuizTopicRepository
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

class QuizTopicRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource,
    private val topicDao: QuizTopicDao
): QuizTopicRepository{

    override suspend fun getQuizTopics(): Result<List<QuizTopic>, DataError> {
        return when (val result = remoteDataSource.getQuizTopics()) {
            is Result.Success -> {
                val quizTopicsDto = result.data
                topicDao.clearAllQuizTopics()
                topicDao.insertQuizTopics(quizTopicsDto.toQuizTopicsEntity())
                Result.Success(quizTopicsDto.toQuizTopics())
            }

            is Result.Failure -> {
                val cachedTopic = topicDao.getAllQuizTopics()
                if (cachedTopic.isNotEmpty()) {
                    Result.Success(cachedTopic.entityToQuizTopics())
                } else {
                    result
                }
            }
        }
    }

    override suspend fun getQuizTopicByCode(topicCode: Int): Result<QuizTopic, DataError> {
        return try {
            val topicEntity = topicDao.getQuizTopicByCode(topicCode = topicCode)
            if (topicEntity != null) {
                Result.Success(topicEntity.entityToQuizTopic())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "Quiz Topic not Fount."))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}