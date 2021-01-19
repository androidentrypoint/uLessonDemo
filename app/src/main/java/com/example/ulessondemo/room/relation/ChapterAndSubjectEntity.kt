package com.example.ulessondemo.room.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.SubjectEntity

data class ChapterAndSubjectEntity(
    @Embedded
    val chapterEntity: ChapterEntity,
    @Relation(parentColumn = "subjectId", entityColumn = "id")
    val subjectEntity: SubjectEntity
)
