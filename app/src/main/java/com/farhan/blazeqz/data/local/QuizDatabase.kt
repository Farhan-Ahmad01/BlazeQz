package com.farhan.blazeqz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.farhan.blazeqz.data.local.converter.OptionListConverters
import com.farhan.blazeqz.data.local.dao.QuestionsCountDao
import com.farhan.blazeqz.data.local.dao.QuizQuestionDao
import com.farhan.blazeqz.data.local.dao.QuizTopicDao
import com.farhan.blazeqz.data.local.dao.UserAnswerDao
import com.farhan.blazeqz.data.local.entity.QuestionsCountEntity
import com.farhan.blazeqz.data.local.entity.QuizQuestionEntity
import com.farhan.blazeqz.data.local.entity.QuizTopicEntity
import com.farhan.blazeqz.data.local.entity.UserAnswerEntity

@Database(
    version = 4,
    entities = [
        QuizTopicEntity::class,
        QuizQuestionEntity::class,
    UserAnswerEntity::class,
    QuestionsCountEntity::class
    ]
)
@TypeConverters(
    OptionListConverters::class
)
abstract class QuizDatabase: RoomDatabase() {

    abstract fun quizTopicDao(): QuizTopicDao

    abstract fun quizQuestionDao(): QuizQuestionDao

    abstract fun userAnswerDao(): UserAnswerDao

    abstract fun questionsCountDao(): QuestionsCountDao

}