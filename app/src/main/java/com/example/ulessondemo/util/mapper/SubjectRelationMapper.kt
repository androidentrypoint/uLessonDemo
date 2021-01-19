package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.model.ChapterWithLessons
import com.example.ulessondemo.model.Subject
import com.example.ulessondemo.model.SubjectWithChapters
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.ChapterWithLessonsEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import javax.inject.Inject

class SubjectRelationMapper @Inject constructor(
    private val subjectMapper: Mapper<SubjectEntity, Subject>,
    private val chapterMapper: Mapper<ChapterWithLessonsEntity, ChapterWithLessons>
) : Mapper<SubjectWithChapterEntity, SubjectWithChapters> {
    override suspend fun map(from: SubjectWithChapterEntity): SubjectWithChapters {
        return with(from) {
            SubjectWithChapters(subjectMapper.map(subject), chapterMapper.mapList(chapters))
        }
    }

    override suspend fun mapInverse(from: SubjectWithChapters): SubjectWithChapterEntity {
        return with(from) {
            SubjectWithChapterEntity(
                subjectMapper.mapInverse(subject),
                chapterMapper.mapListInverse(chapters)
            )
        }
    }
}