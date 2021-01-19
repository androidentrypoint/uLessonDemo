package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.room.entity.LessonEntity
import javax.inject.Inject

class LessonMapper @Inject constructor(
) : Mapper<LessonEntity, Lesson> {
    override suspend fun map(from: LessonEntity): Lesson {
        return with(from) {
            Lesson(id, name, iconUrl, mediaUrl, chapterId, lastWatched)
        }
    }

    override suspend fun mapInverse(from: Lesson): LessonEntity {
        return with(from) {
            LessonEntity(id, name, iconUrl, mediaUrl, chapterId, lastWatched)
        }
    }

}