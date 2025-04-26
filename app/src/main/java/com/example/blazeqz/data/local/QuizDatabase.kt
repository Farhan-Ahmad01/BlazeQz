package com.example.blazeqz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.blazeqz.data.local.converter.OptionListConverters
import com.example.blazeqz.data.local.dao.QuestionsCountDao
import com.example.blazeqz.data.local.dao.QuizQuestionDao
import com.example.blazeqz.data.local.dao.QuizTopicDao
import com.example.blazeqz.data.local.dao.UserAnswerDao
import com.example.blazeqz.data.local.entity.QuestionsCountEntity
import com.example.blazeqz.data.local.entity.QuizQuestionEntity
import com.example.blazeqz.data.local.entity.QuizTopicEntity
import com.example.blazeqz.data.local.entity.UserAnswerEntity

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