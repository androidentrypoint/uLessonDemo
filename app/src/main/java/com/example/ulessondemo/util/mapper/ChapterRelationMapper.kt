package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.model.Chapter
import com.example.ulessondemo.model.ChapterWithLessons
import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.relation.ChapterWithLessonsEntity
import javax.inject.Inject

class ChapterRelationMapper @Inject constructor(
    private val chapterMapper: Mapper<ChapterEntity, Chapter>,
    private val lessonMapper: Mapper<LessonEntity, Lesson>
) : Mapper<ChapterWithLessonsEntity, ChapterWithLessons> {
    override suspend fun map(from: ChapterWithLessonsEntity): ChapterWithLessons {
        return with(from) {
            ChapterWithLessons(chapterMapper.map(chapter), lessonMapper.mapList(lessons))
        }
    }

    override suspend fun mapInverse(from: ChapterWithLessons): ChapterWithLessonsEntity {
        return with(from) {
            ChapterWithLessonsEntity(
                chapterMapper.mapInverse(chapter),
                lessonMapper.mapListInverse(lessons)
            )
        }
    }

}