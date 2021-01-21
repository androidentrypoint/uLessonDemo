package com.example.ulessondemo.util

import com.example.ulessondemo.room.dao.ISubjectDao
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubjectDaoFake : ISubjectDao {

    override suspend fun getAllSubjects(): List<SubjectWithChapterEntity> {
        return RoomFakeGenerator.getSubjects()
    }

    override fun getAllSubjectsAsFlow(): Flow<List<SubjectWithChapterEntity>> {
        return flow { emit(RoomFakeGenerator.getSubjects()) }
    }

    override suspend fun deleteAll() {
        return
    }

    override suspend fun insertSubject(subjectEntity: SubjectEntity) {
        return
    }

    override suspend fun insertSubjects(subjectWithChapterEntities: List<SubjectWithChapterEntity>) {
        return
    }
}