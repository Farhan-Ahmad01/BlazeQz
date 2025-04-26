package com.example.blazeqz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.blazeqz.data.local.entity.QuestionsCountEntity
import com.example.blazeqz.domain.model.QuestionsCount

@Dao
interface QuestionsCountDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionsCount(questionsCountEntity: QuestionsCountEntity)

    @Query("SELECT * FROM questions_count")
    suspend fun getQuestionsCount(): QuestionsCountEntity

}