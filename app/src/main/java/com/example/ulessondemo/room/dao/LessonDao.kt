package com.example.ulessondemo.room.dao

import androidx.room.*
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.relation.LessonAndChapterEntity
import kotlinx.coroutines.flow.Flow

interface ILessonDao {
    @Transaction
    @Query("SELECT * FROM LessonEntity WHERE lastWatched IS NOT NULL ORDER BY lastWatched DESC")
    suspend fun getRecentlyWatchedLessons(): List<LessonAndChapterEntity>

    @Transaction
    @Query("SELECT * FROM LessonEntity WHERE lastWatched IS NOT NULL ORDER BY lastWatched DESC")
    fun getRecentlyWatchedLessonsAsFlow(): Flow<List<LessonAndChapterEntity>>

    @Query("SELECT * FROM LessonEntity")
    suspend fun getAllLessons(): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLesson(lessonEntity: LessonEntity)
}

@Dao
abstract class LessonDao : ILessonDao