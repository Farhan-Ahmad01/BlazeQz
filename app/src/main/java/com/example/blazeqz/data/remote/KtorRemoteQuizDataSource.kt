package com.example.blazeqz.data.remote

import android.util.Log
import com.example.blazeqz.data.remote.dto.IssueReportDto
import com.example.blazeqz.data.remote.dto.QuestionsCountDto
import com.example.blazeqz.data.remote.dto.QuizQuestionDto
import com.example.blazeqz.data.remote.dto.QuizTopicDto
import com.example.blazeqz.data.util.Constant.BASE_URL
import com.example.blazeqz.domain.util.DataError
import com.example.blazeqz.domain.util.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class KtorRemoteQuizDataSource(
    private val httpClient: HttpClient
) : RemoteQuizDataSource {
    override suspend fun getQuizTopics(): Result<List<QuizTopicDto>, DataError> {
        return safeCall<List<QuizTopicDto>> {
            httpClient.get(urlString = "$BASE_URL/quiz/topics")
        }
    }

    override suspend fun getQuizQuestions(topicCode: Int): Result<List<QuizQuestionDto>, DataError> {
        return safeCall<List<QuizQuestionDto>> {
            httpClient.get(urlString = "$BASE_URL/quiz/questions/random") {
                parameter("topicCode", topicCode)
            }
        }
    }

    override suspend fun insertIssueReport(report: IssueReportDto): Result<Unit, DataError> {
        return safeCall<Unit> {
            httpClient.post(urlString = "$BASE_URL/report/issues") {
                setBody(report)
            }
        }
    }

    override suspend fun getQuestionsCount(): Result<QuestionsCountDto, DataError> {
        Log.d("QuestionsCountCheck", "API calling now")
        return safeCall<QuestionsCountDto> {
            Log.d("QuestionsCountCheck", "making a call......")
            httpClient.get(urlString = "$BASE_URL/quiz/count")
        }
    }
}