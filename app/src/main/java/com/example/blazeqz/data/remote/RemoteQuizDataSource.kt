package com.example.blazeqz.data.remote

import com.example.blazeqz.data.remote.dto.IssueReportDto
import com.example.blazeqz.data.remote.dto.QuestionsCountDto
import com.example.blazeqz.data.remote.dto.QuizQuestionDto
import com.example.blazeqz.data.remote.dto.QuizTopicDto
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result

interface RemoteQuizDataSource {

    suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError>

    suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>, DataError>

    suspend fun insertIssueReport(report: IssueReportDto): Result<Unit, DataError>

    suspend fun getQuestionsCount(): Result<QuestionsCountDto, DataError>

}