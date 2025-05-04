package com.farhan.blazeqz.data.repository

import android.util.Log
import com.farhan.blazeqz.data.local.dao.QuestionsCountDao
import com.farhan.blazeqz.data.mapper.entityToQuestionsCount
import com.farhan.blazeqz.data.mapper.toQuestionCountEntity
import com.farhan.blazeqz.data.mapper.toQuestionsCount
import com.farhan.blazeqz.data.remote.RemoteQuizDataSource
import com.farhan.blazeqz.domain.model.QuestionsCount
import com.farhan.blazeqz.domain.repository.QuestionsCountRepository
import com.farhan.blazeqz.domain.util.DataError
import com.farhan.blazeqz.domain.util.Result

class QuestionsCountRepositoryImpl(
    private val remoteDataSource: RemoteQuizDataSource,
    private val questionsCountDao: QuestionsCountDao
): QuestionsCountRepository {

    override suspend fun getQuestionsCount(): Result<QuestionsCount, DataError> {
        Log.d("QuestionsCountCheck", "Repo Imple initiated")
        return when (val result = remoteDataSource.getQuestionsCount()) {
            is Result.Success -> {
                Log.d("QuestionsCountCheck", "Got result from remoteDataSource, ${result.data.count}")
                val questionsCountDto = result.data
                questionsCountDao.insertQuestionsCount(questionsCountDto.toQuestionCountEntity())
                Result.Success(questionsCountDto.toQuestionsCount())
            }
            is Result.Failure -> {
                Log.d("QuestionsCountCheck", "Check your network connection")
                val cachedQuestions = questionsCountDao.getQuestionsCount()
                if (cachedQuestions != null) {
                    Log.d("QuestionsCountCheck", "failed from remoteDataSource, cachedData= ${cachedQuestions.count}")
                    Result.Success(cachedQuestions.entityToQuestionsCount())
                } else {
                    Log.d("QuestionsCountCheck", "failed from remoteDataSource, and cachedData is null= ${cachedQuestions.count}")
                    result
                }
            }
        }
    }
}