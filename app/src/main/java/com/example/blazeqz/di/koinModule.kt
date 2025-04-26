package com.example.blazeqz.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.blazeqz.data.local.DataStoreFactory
import com.example.blazeqz.data.local.DatabaseFactory
import com.example.blazeqz.data.local.QuizDatabase
import com.example.blazeqz.data.remote.HttpClientFactory
import com.example.blazeqz.data.remote.KtorRemoteQuizDataSource
import com.example.blazeqz.data.remote.RemoteQuizDataSource
import com.example.blazeqz.data.repository.IssueReportRepositoryImpl
import com.example.blazeqz.data.repository.QuestionsCountRepositoryImpl
import com.example.blazeqz.data.repository.QuizQuestionRepositoryImpl
import com.example.blazeqz.data.repository.QuizTopicRepositoryImpl
import com.example.blazeqz.data.repository.UserPreferencesRepositoryImpl
import com.example.blazeqz.domain.repository.IssueReportRepository
import com.example.blazeqz.domain.repository.QuestionsCountRepository
import com.example.blazeqz.domain.repository.QuizQuestionRepository
import com.example.blazeqz.domain.repository.QuizTopicRepository
import com.example.blazeqz.domain.repository.UserPreferencesRepository
import com.example.blazeqz.presentation.dashboard.DashboardViewModel
import com.example.blazeqz.presentation.issue_report.IssueReportViewModel
import com.example.blazeqz.presentation.quiz.QuizViewModel
import com.example.blazeqz.presentation.result.ResultViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

val koinModule = module {

    single { HttpClientFactory.create() }

    singleOf(::KtorRemoteQuizDataSource).bind<RemoteQuizDataSource>()

    //local
    single { DataStoreFactory.create(get()) }
    single { DatabaseFactory.create(get()) }
    single { get<QuizDatabase>().quizTopicDao() }
    single { get<QuizDatabase>().quizQuestionDao() }
    single { get<QuizDatabase>().userAnswerDao() }
    single { get<QuizDatabase>().questionsCountDao() }

    //Repository
    singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
    singleOf(::QuestionsCountRepositoryImpl).bind<QuestionsCountRepository>()
    singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
    singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()
    singleOf(::UserPreferencesRepositoryImpl).bind<UserPreferencesRepository>()

    //ViewModel
    viewModelOf(::QuizViewModel)
    viewModelOf(::DashboardViewModel)
    viewModelOf(::ResultViewModel)
    viewModelOf(::IssueReportViewModel)

}