package com.example.ulessondemo.repository

import com.example.ulessondemo.model.Lesson
import com.example.ulessondemo.model.LessonWithSubjectAndChapter
import com.example.ulessondemo.model.SubjectWithChapters
import com.example.ulessondemo.network.NetworkProcessor
import com.example.ulessondemo.network.UService
import com.example.ulessondemo.network.model.SubjectDTO
import com.example.ulessondemo.room.dao.ILessonDao
import com.example.ulessondemo.room.dao.ISubjectDao
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.relation.LessonAndChapterEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import com.example.ulessondemo.state.HomeState
import com.example.ulessondemo.util.mapper.Mapper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.akefestival.core.util.DispatcherProvider
import org.akefestival.core.util.NetworkStatus
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val service: UService,
    private val subjectDao: ISubjectDao,
    private val lessonDao: ILessonDao,
    private val dispatcherProvider: DispatcherProvider,
    private val networkProcessor: NetworkProcessor,
    private val subjectMapper: Mapper<SubjectWithChapterEntity, SubjectWithChapters>,
    private val lessonRelationMapper: Mapper<LessonAndChapterEntity, LessonWithSubjectAndChapter>,
    private val subjectDTOMapper: Mapper<SubjectDTO, SubjectWithChapterEntity>,
    private val lessonMapper: Mapper<LessonEntity, Lesson>
) : Repository, NetworkProcessor by networkProcessor, DispatcherProvider by dispatcherProvider {

    override fun getHomeItems(): Flow<NetworkStatus<HomeState>> = flow<NetworkStatus<HomeState>> {
        emit(
            NetworkStatus.Loading(
                HomeState(
                    subjectMapper.mapList(subjectDao.getAllSubjects()),
                    lessonRelationMapper.mapList(lessonDao.getRecentlyWatchedLessons())
                )
            )
        )
        when (val response = processNetworkResponse { service.getSubjects() }) {
            is NetworkStatus.Success -> {
                subjectDao.insertSubjects(subjectDTOMapper.mapList(response.data))
                emitAll(
                    subjectDao.getAllSubjectsAsFlow()
                        .combineTransform(lessonDao.getRecentlyWatchedLessonsAsFlow()) { subjects, lessons ->
                            emit(
                                NetworkStatus.Success(
                                    HomeState(
                                        subjectMapper.mapList(subjects),
                                        lessonRelationMapper.mapList(lessons)
                                    )
                                )
                            )
                        })
            }
            is NetworkStatus.Loading -> {
            }
            is NetworkStatus.Error -> {
                emit(NetworkStatus.Error(response.message))
            }
        }
    }.flowOn(io())

    override suspend fun updateLastWatched(lesson: Lesson) {
        withContext(io()) {
            lessonDao.insertLesson(
                lessonMapper.mapInverse(
                    lesson.copy(
                        lastWatched = System.currentTimeMillis().toString()
                    )
                )
            )
        }
    }
}