package com.example.ulessondemo.room.dao

import androidx.room.*
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.relation.LessonAndChapterEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LessonDao {

    @Transaction
    @Query("SELECT * FROM LessonEntity WHERE lastWatched IS NOT NULL ORDER BY lastWatched DESC")
    abstract suspend fun getRecentlyWatchedLessons(): List<LessonAndChapterEntity>

    @Transaction
    @Query("SELECT * FROM LessonEntity WHERE lastWatched IS NOT NULL ORDER BY lastWatched DESC")
    abstract fun getRecentlyWatchedLessonsAsFlow(): Flow<List<LessonAndChapterEntity>>

    @Query("SELECT * FROM LessonEntity")
    abstract suspend fun getAllLessons(): List<LessonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertLesson(lessonEntity: LessonEntity)

}