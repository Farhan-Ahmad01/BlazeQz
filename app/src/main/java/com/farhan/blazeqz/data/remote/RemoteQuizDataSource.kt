package com.farhan.blazeqz.data.remote

import com.farhan.blazeqz.data.remote.dto.IssueReportDto
import com.farhan.blazeqz.data.remote.dto.QuestionsCountDto
import com.farhan.blazeqz.data.remote.dto.QuizQuestionDto
import com.farhan.blazeqz.data.remote.dto.QuizTopicDto
import com.farhan.blazeqz.domain.util.DataError
import com.farhan.blazeqz.domain.util.Result

interface RemoteQuizDataSource {

    suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError>

    suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>, DataError>

    suspend fun insertIssueReport(report: IssueReportDto): Result<Unit, DataError>

    suspend fun getQuestionsCount(): Result<QuestionsCountDto, DataError>

}