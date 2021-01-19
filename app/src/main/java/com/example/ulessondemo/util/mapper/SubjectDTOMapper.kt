package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.network.model.ChapterDTO
import com.example.ulessondemo.network.model.LessonDTO
import com.example.ulessondemo.network.model.SubjectDTO
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.ChapterWithLessonsEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import javax.inject.Inject

class SubjectDTOMapper @Inject constructor(
) : Mapper<SubjectDTO, SubjectWithChapterEntity> {
    override suspend fun map(from: SubjectDTO): SubjectWithChapterEntity {
        return with(from) {
            SubjectWithChapterEntity(SubjectEntity(id, name, iconUrl, 0),
                chapters.map {
                    ChapterWithLessonsEntity(
                        ChapterEntity(it.id, it.name, id),
                        it.lessons.map { lessonDTO ->
                            with(lessonDTO) {
                                LessonEntity(id, name, iconUrl, mediaUrl, chapterId, null)
                            }
                        }
                    )
                })
        }
    }

    override suspend fun mapInverse(from: SubjectWithChapterEntity): SubjectDTO {
        return with(from) {
            SubjectDTO(
                subject.id,
                subject.name,
                subject.iconUrl,
                chapters.map { chapterWithLessonsEntity ->
                    with(chapterWithLessonsEntity.chapter) {
                        ChapterDTO(id, name, chapterWithLessonsEntity.lessons.map {
                            with(it) {
                                LessonDTO(id, name, iconUrl, mediaUrl, chapterId, subjectId)
                            }
                        })
                    }
                })
        }
    }

    override suspend fun mapList(from: List<SubjectDTO>): List<SubjectWithChapterEntity> {
        return super.mapList(from).mapIndexed { index, subjectWithChapterEntity ->
            subjectWithChapterEntity.copy(
                subject = subjectWithChapterEntity.subject.copy(drawableAndColorIndex = index)
            )
        }
    }
}