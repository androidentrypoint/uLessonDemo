package com.example.ulessondemo.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity

data class ChapterWithLessonsEntity(
    @Embedded
    val chapter: ChapterEntity,
    @Relation(parentColumn = "id", entityColumn = "chapterId")
    val lessons: List<LessonEntity>
)
