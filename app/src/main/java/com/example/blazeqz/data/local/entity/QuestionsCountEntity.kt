package com.example.blazeqz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.blazeqz.data.util.Constant.QUESTIONS_COUNT_TABLE_NAME

@Entity(tableName = QUESTIONS_COUNT_TABLE_NAME)
data class QuestionsCountEntity(
    @PrimaryKey
    val id: String,
    val count: Int
)
