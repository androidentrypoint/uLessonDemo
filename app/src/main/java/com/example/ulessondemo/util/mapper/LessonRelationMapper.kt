package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.model.Chapter
import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.model.LessonWithSubjectAndChapter
import com.example.ulessondemo.model.Subject
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.ChapterAndSubjectEntity
import com.example.ulessondemo.room.relation.LessonAndChapterEntity
import javax.inject.Inject

class LessonRelationMapper @Inject constructor(
    private val subjectMapper: Mapper<SubjectEntity, Subject>,
    private val chapterMapper: Mapper<ChapterEntity, Chapter>,
    private val lessonMapper: Mapper<LessonEntity, Lesson>
) : Mapper<LessonAndChapterEntity, LessonWithSubjectAndChapter> {
    override suspend fun map(from: LessonAndChapterEntity): LessonWithSubjectAndChapter {
        return with(from) {
            LessonWithSubjectAndChapter(
                lessonMapper.map(lessonEntity),
                chapterMapper.map(chapterEntity.chapterEntity),
                subjectMapper.map(chapterEntity.subjectEntity)
            )
        }
    }

    override suspend fun mapInverse(from: LessonWithSubjectAndChapter): LessonAndChapterEntity {
        return with(from) {
            LessonAndChapterEntity(
                lessonMapper.mapInverse(lesson),
                ChapterAndSubjectEntity(
                    chapterMapper.mapInverse(chapter),
                    subjectMapper.mapInverse(subject)
                )
            )
        }
    }
}