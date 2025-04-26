package com.example.blazeqz.domain.repository

import com.example.blazeqz.domain.model.QuestionsCount
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

interface QuestionsCountRepository {

    suspend fun getQuestionsCount(): Result<QuestionsCount, DataError>

}