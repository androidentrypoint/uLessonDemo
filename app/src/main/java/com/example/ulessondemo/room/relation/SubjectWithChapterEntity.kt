package com.example.ulessondemo.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.SubjectEntity

data class SubjectWithChapterEntity(
    @Embedded
    val subject: SubjectEntity,
    @Relation(entity = ChapterEntity::class, parentColumn = "id", entityColumn = "subjectId")
    val chapters: List<ChapterWithLessonsEntity>
)
