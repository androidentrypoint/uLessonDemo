package com.example.ulessondemo.room.dao

import androidx.room.*
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import kotlinx.coroutines.flow.Flow
import org.akefestival.core.room.UDatabase

@Dao
abstract class SubjectDao(private val db: UDatabase) {

    @Transaction
    @Query("SELECT * FROM SubjectEntity")
    abstract suspend fun getAllSubjects(): List<SubjectWithChapterEntity>

    @Transaction
    @Query("SELECT * FROM SubjectEntity")
    abstract fun getAllSubjectsAsFlow(): Flow<List<SubjectWithChapterEntity>>

    @Query("DELETE FROM SubjectEntity")
    abstract suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertSubject(subjectEntity: SubjectEntity)

    @Transaction
    open suspend fun insertSubjects(subjectWithChapterEntities: List<SubjectWithChapterEntity>) {
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