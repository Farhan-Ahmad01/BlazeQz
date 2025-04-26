package com.example.blazeqz.domain.repository

import androidx.datastore.preferences.core.intPreferencesKey
import com.example.blazeqz.data.util.Constant.CORRECT_ANSWERS_PREF_KEY
import com.example.blazeqz.data.util.Constant.QUESTIONS_ATTEMPTED_PREF_KEY
import kotlinx.coroutines.flow.Flow

interface UserPreferencesRepository {

    fun getQuestionAttempted(): Flow<Int>

    fun getCorrectAnswers(): Flow<Int>

    fun getUsername(): Flow<String>

    suspend fun saveScore(questionAttempted: Int, correctAnswers: Int)

    suspend fun saveUsername(name: String)

}