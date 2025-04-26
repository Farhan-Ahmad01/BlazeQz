package com.example.blazeqz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.blazeqz.data.local.entity.UserAnswerEntity
import com.example.blazeqz.domain.model.UserAnswer

@Dao
interface UserAnswerDao {

    @Query("SELECT * FROM user_answers")
    suspend fun getAllUserAnswers(): List<UserAnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserAnswer(answers: List<UserAnswerEntity>)

    @Query("DELETE FROM user_answers")
    suspend fun clearAllAnswers()

}