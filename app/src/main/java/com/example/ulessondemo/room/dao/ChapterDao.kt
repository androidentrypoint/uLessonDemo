package com.example.ulessondemo.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.ulessondemo.room.entity.ChapterEntity
import org.akefestival.core.room.UDatabase

@Dao
abstract class ChapterDao(private val db: UDatabase) {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertChapter(chapterEntity: ChapterEntity)
}