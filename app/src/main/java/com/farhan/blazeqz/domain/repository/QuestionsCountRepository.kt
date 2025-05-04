package com.farhan.blazeqz.domain.repository

import com.farhan.blazeqz.domain.model.QuestionsCount
import com.farhan.blazeqz.domain.util.DataError
import com.farhan.blazeqz.domain.util.Result

interface QuestionsCountRepository {

    suspend fun getQuestionsCount(): Result<QuestionsCount, DataError>

}