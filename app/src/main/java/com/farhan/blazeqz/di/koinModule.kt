package com.farhan.blazeqz.di

import com.farhan.blazeqz.data.local.DataStoreFactory
import com.farhan.blazeqz.data.local.DatabaseFactory
import com.farhan.blazeqz.data.local.QuizDatabase
import com.farhan.blazeqz.data.remote.HttpClientFactory
import com.farhan.blazeqz.data.remote.KtorRemoteQuizDataSource
import com.farhan.blazeqz.data.remote.RemoteQuizDataSource
import com.farhan.blazeqz.data.repository.IssueReportRepositoryImpl
import com.farhan.blazeqz.data.repository.QuestionsCountRepositoryImpl
import com.farhan.blazeqz.data.repository.QuizQuestionRepositoryImpl
import com.farhan.blazeqz.data.repository.QuizTopicRepositoryImpl
import com.farhan.blazeqz.data.repository.UserPreferencesRepositoryImpl
import com.farhan.blazeqz.domain.repository.IssueReportRepository
import com.farhan.blazeqz.domain.repository.QuestionsCountRepository
import com.farhan.blazeqz.domain.repository.QuizQuestionRepository
import com.farhan.blazeqz.domain.repository.QuizTopicRepository
import com.farhan.blazeqz.domain.repository.UserPreferencesRepository
import com.farhan.blazeqz.presentation.dashboard.DashboardViewModel
import com.farhan.blazeqz.presentation.issue_report.IssueReportViewModel
import com.farhan.blazeqz.presentation.quiz.QuizViewModel
import com.farhan.blazeqz.presentation.result.ResultViewModel
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