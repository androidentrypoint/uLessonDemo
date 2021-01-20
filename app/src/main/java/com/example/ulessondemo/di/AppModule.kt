package com.example.ulessondemo.di

import android.content.Context
import com.example.ulessondemo.model.*
import com.example.ulessondemo.network.NetworkProcessor
import com.example.ulessondemo.network.NetworkProcessorImpl
import com.example.ulessondemo.network.UService
import com.example.ulessondemo.network.model.SubjectDTO
import com.example.ulessondemo.repository.Repository
import com.example.ulessondemo.repository.RepositoryImpl
import com.example.ulessondemo.room.dao.*
import com.example.ulessondemo.room.entity.ChapterEntity
import com.example.ulessondemo.room.entity.LessonEntity
import com.example.ulessondemo.room.entity.SubjectEntity
import com.example.ulessondemo.room.relation.ChapterWithLessonsEntity
import com.example.ulessondemo.room.relation.LessonAndChapterEntity
import com.example.ulessondemo.room.relation.SubjectWithChapterEntity
import com.example.ulessondemo.util.mapper.*
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import org.akefestival.core.room.UDatabase
import org.akefestival.core.util.DefaultDispatcherProvider
import org.akefestival.core.util.DispatcherProvider
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideService(): UService {
        return Retrofit.Builder()
            .baseUrl("https://jackiechanbruteforce.ulesson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocalDb(
        @ApplicationContext context: Context,
    ): UDatabase {
        return UDatabase.getInstance(
            context
        )
    }

    @Singleton
    @Provides
    fun provideSubjectDao(db: UDatabase) = db.subjectDao()

    @Singleton
    @Provides
    fun provideChapterDao(db: UDatabase) = db.chapterDao()

    @Singleton
    @Provides
    fun provideLessonDao(db: UDatabase) = db.lessonDao()
}

@Module
@InstallIn(ApplicationComponent::class)
abstract class BindingModule {

    @Singleton
    @Binds
    abstract fun bindSubjectDao(subjectDao: SubjectDao): ISubjectDao

    @Singleton
    @Binds
    abstract fun bindChapterDao(chapterDao: ChapterDao): IChapterDao

    @Singleton
    @Binds
    abstract fun bindLessonDao(lessonDao: LessonDao): ILessonDao

    @Singleton
    @Binds
    abstract fun bindRepository(
        repository: RepositoryImpl
    ): Repository

    @Singleton
    @Binds
    abstract fun bindNetworkProcessor(
        networkProcessor: NetworkProcessorImpl
    ): NetworkProcessor

    @Singleton
    @Binds
    abstract fun bindCoroutineDispatcher(
        dispatcherProvider: DefaultDispatcherProvider
    ): DispatcherProvider

    @Singleton
    @Binds
    abstract fun bindChapterMapper(
        mapper: ChapterMapper
    ): Mapper<ChapterEntity, Chapter>

    @Singleton
    @Binds
    abstract fun bindChapterRelationMapper(
        mapper: ChapterRelationMapper
    ): Mapper<ChapterWithLessonsEntity, ChapterWithLessons>

    @Singleton
    @Binds
    abstract fun bindLessonMapper(
        mapper: LessonMapper
    ): Mapper<LessonEntity, Lesson>

    @Singleton
    @Binds
    abstract fun bindLessonRelationMapper(
        mapper: LessonRelationMapper
    ): Mapper<LessonAndChapterEntity, LessonWithSubjectAndChapter>

    @Singleton
    @Binds
    abstract fun binSubjectDTOMapper(
        mapper: SubjectDTOMapper
    ): Mapper<SubjectDTO, SubjectWithChapterEntity>

    @Singleton
    @Binds
    abstract fun bindSubjectMapper(
        mapper: SubjectMapper
    ): Mapper<SubjectEntity, Subject>

    @Singleton
    @Binds
    abstract fun bindSubjectRelationMapper(
        mapper: SubjectRelationMapper
    ): Mapper<SubjectWithChapterEntity, SubjectWithChapters>
}