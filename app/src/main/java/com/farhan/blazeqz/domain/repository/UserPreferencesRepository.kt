package com.farhan.blazeqz.domain.repository

import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getQuestionAttempted(): Flow<Int>

    fun getCorrectAnswers(): Flow<Int>

    fun getUsername(): Flow<String>

    suspend fun saveScore(questionAttempted: Int, correctAnswers: Int)

    suspend fun saveUsername(name: String)

    suspend fun savaTotalQuestionsCount(totalQuestionsCount: Int)

}