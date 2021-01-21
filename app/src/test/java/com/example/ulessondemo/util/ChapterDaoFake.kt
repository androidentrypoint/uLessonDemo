package com.example.ulessondemo.util

import com.example.ulessondemo.room.dao.IChapterDao
import com.example.ulessondemo.room.entity.ChapterEntity

class ChapterDaoFake : IChapterDao {
    override suspend fun insertChapter(chapterEntity: ChapterEntity) {
        return
    }
}