package com.example.blazeqz.data.repository

import com.example.blazeqz.data.local.dao.QuizQuestionDao
import com.example.blazeqz.data.local.dao.UserAnswerDao
import com.example.blazeqz.data.mapper.entityToQuizQuestion
import com.example.blazeqz.data.mapper.entityToQuizQuestions
import com.example.blazeqz.data.mapper.toQuizQuestions
import com.example.blazeqz.data.mapper.toQuizQuestionsEntity
import com.example.blazeqz.data.mapper.toUserAnswers
import com.example.blazeqz.data.mapper.toUserAnswersEntity
import com.example.blazeqz.data.remote.RemoteQuizDataSource
import com.example.blazeqz.domain.model.QuizQuestion
import com.example.blazeqz.domain.model.UserAnswer
import com.example.blazeqz.domain.repository.QuizQuestionRepository
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

class QuizQuestionRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource,
    private val questionDao: QuizQuestionDao,
    private val answerDao: UserAnswerDao
): QuizQuestionRepository {

    override suspend fun fetchAndSaveQuizQuestions(topicCode: Int): Result<List<QuizQuestion>, DataError> {
        return when (val result = remoteDataSource.getQuizQuestions(topicCode)) {
            is Result.Success -> {
                val questionDto = result.data
                answerDao.clearAllAnswers()
                questionDao.clearAllQuizQuestions()
                questionDao.insertQuizQuestions(questionDto.toQuizQuestionsEntity())
                Result.Success(questionDto.toQuizQuestions())
            }
            is Result.Failure -> result
        }
    }

    override suspend fun getQuizQuestions(): Result<List<QuizQuestion>, DataError> {
        return try {
            val questionsEntity = questionDao.getAllQuizQuestions()
            if (questionsEntity.isNotEmpty()) {
                Result.Success(questionsEntity.entityToQuizQuestions())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "No Quiz Questions Found"))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getQuizQuestionById(questionId: String): Result<QuizQuestion, DataError> {
        return try {
            val questionEntity = questionDao.getQuizQuestionById(questionId)
            if (questionEntity != null) {
                Result.Success(questionEntity.entityToQuizQuestion())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "Quiz Question not Found"))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun saveUserAnswers(userAnswers: List<UserAnswer>): Result<Unit, DataError> {
        return try {
            val answerEntity = userAnswers.toUserAnswersEntity()
            answerDao.insertUserAnswer(answerEntity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }

    override suspend fun getUserAnswers(): Result<List<UserAnswer>, DataError> {
        return try {
            val answersEntity = answerDao.getAllUserAnswers()
            if (answersEntity.isNotEmpty()) {
                Result.Success(answersEntity.toUserAnswers())
            } else {
                Result.Failure(DataError.Unknown(errorMessage = "No User Answers Found"))
            }
        } catch (e: Exception) {
            Result.Failure(DataError.Unknown(e.message))
        }
    }
}