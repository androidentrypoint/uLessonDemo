package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.model.Chapter
import com.example.ulessondemo.room.entity.ChapterEntity
import javax.inject.Inject

class ChapterMapper @Inject constructor(
) : Mapper<ChapterEntity, Chapter> {
    override suspend fun map(from: ChapterEntity): Chapter {
        return with(from) {
            Chapter(id, name, subjectId)
        }
    }

    override suspend fun mapInverse(from: Chapter): ChapterEntity {
        return with(from) {
            ChapterEntity(id, name, subjectId)
        }
    }

}