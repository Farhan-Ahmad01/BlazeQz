package com.farhan.blazeqz.data.local

import android.content.Context
import androidx.room.Room
import com.farhan.blazeqz.data.util.Constant.DATABASE_NAME

object DatabaseFactory {

    fun create(context: Context): QuizDatabase {
        return Room
            .databaseBuilder(
                context = context.applicationContext,
                klass = QuizDatabase::class.java,
                name = DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }

}