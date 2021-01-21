package com.example.ulessondemo.util

import com.example.ulessondemo.room.dao.ILessonDao
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.relation.ChapterAndSubjectEntity
import com.example.ulessondemo.room.relation.LessonAndChapterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LessonDaoFake : ILessonDao {
    override suspend fun getRecentlyWatchedLessons(): List<LessonAndChapterEntity> {
        return RoomFakeGenerator.getSubjects().flatMap { subject ->
            subject.chapters.flatMap { chapter ->
                chapter.lessons.mapNotNull {
                    if (it.lastWatched == null) null else LessonAndChapterEntity(
                        it,
                        ChapterAndSubjectEntity(chapter.chapter, subject.subject)
                    )
                }
            }
        }
    }

    override fun getRecentlyWatchedLessonsAsFlow(): Flow<List<LessonAndChapterEntity>> {
        return flow { emit(getRecentlyWatchedLessons()) }
    }

    override suspend fun getAllLessons(): List<LessonEntity> {
        return RoomFakeGenerator.getSubjects()
            .flatMap { subject -> subject.chapters.flatMap { chapter -> chapter.lessons } }
    }

    override suspend fun insertLesson(lessonEntity: LessonEntity) {
        return
    }
}