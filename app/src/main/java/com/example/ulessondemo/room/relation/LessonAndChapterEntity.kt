package com.example.ulessondemo.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity

data class LessonAndChapterEntity(
    @Embedded
    val lessonEntity: LessonEntity,
    @Relation(entity = ChapterEntity::class, parentColumn = "chapterId", entityColumn = "id")
    val chapterEntity: ChapterAndSubjectEntity
)
