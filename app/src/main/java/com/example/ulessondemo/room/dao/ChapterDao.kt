package com.example.ulessondemo.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.ulessondemo.room.UDatabase
import com.example.ulessondemo.room.entity.ChapterEntity

interface IChapterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChapter(chapterEntity: ChapterEntity)
}

@Dao
abstract class ChapterDao(private val db: UDatabase) : IChapterDao