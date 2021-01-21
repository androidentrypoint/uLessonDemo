package com.example.ulessondemo.room.dao

import androidx.room.*
import com.example.ulessondemo.room.UDatabase
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import kotlinx.coroutines.flow.Flow

interface ISubjectDao {
    @Transaction
    @Query("SELECT * FROM SubjectEntity")
    suspend fun getAllSubjects(): List<SubjectWithChapterEntity>

    @Transaction
    @Query("SELECT * FROM SubjectEntity")
    fun getAllSubjectsAsFlow(): Flow<List<SubjectWithChapterEntity>>

    @Query("DELETE FROM SubjectEntity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubject(subjectEntity: SubjectEntity)

    @Transaction
    suspend fun insertSubjects(subjectWithChapterEntities: List<SubjectWithChapterEntity>)
}

@Dao
abstract class SubjectDao(private val db: UDatabase) : ISubjectDao {

    @Transaction
    override suspend fun insertSubjects(subjectWithChapterEntities: List<SubjectWithChapterEntity>) {
        val lessons = db.lessonDao().getAllLessons()
        deleteAll()
        subjectWithChapterEntities.forEach { subject ->
            insertSubject(subject.subject)
            subject.chapters.forEach { chapter ->
                db.chapterDao().insertChapter(chapter.chapter)
                chapter.lessons.forEach { lessonEntity ->
                    db.lessonDao().insertLesson(
                        lessonEntity.copy(lastWatched = lessons.firstOrNull { it.id == lessonEntity.id }?.lastWatched)
                    )
                }
            }
        }
    }
}