package com.example.ulessondemo.util

import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import com.example.ulessondemo.util.mapper.SubjectDTOMapper

object RoomFakeGenerator {

    private val subjectMapper = SubjectDTOMapper()

    suspend fun getSubjects(): List<SubjectWithChapterEntity> {
        return subjectMapper.mapList(Util.getMockNetworkResponseData())
    }
}