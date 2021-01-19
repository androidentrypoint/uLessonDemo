package com.example.ulessondemo.util.mapper

import com.example.ulessondemo.model.Subject
import com.example.ulessondemo.room.entity.SubjectEntity
import javax.inject.Inject

class SubjectMapper @Inject constructor(
) : Mapper<SubjectEntity, Subject> {
    override suspend fun map(from: SubjectEntity): Subject {
        return with(from) {
            Subject(id, name, iconUrl, drawableAndColorIndex)
        }
    }

    override suspend fun mapInverse(from: Subject): SubjectEntity {
        return with(from) {
            SubjectEntity(id, name, iconUrl, drawableAndColorIndex)
        }
    }

}