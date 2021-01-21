package com.example.ulessondemo.room

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ulessondemo.util.CoroutineTestRule
import com.example.ulessondemo.util.RoomFakeGenerator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException
import java.util.concurrent.Executors

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class UDatabaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var db: UDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, UDatabase::class.java)
            .setTransactionExecutor(Executors.newSingleThreadExecutor())
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `verify subject dto insertion was successful`() = runBlocking {
        val subjectEntities = RoomFakeGenerator.getSubjects()
        db.subjectDao().insertSubjects(subjectEntities)
        val insertedSubjects = db.subjectDao().getAllSubjects()
        Assert.assertEquals(subjectEntities.size, insertedSubjects.size)
    }

    @Test
    fun `verify cascading deletion was successful`() = runBlocking {
        val subjectEntities = RoomFakeGenerator.getSubjects()
        db.subjectDao().insertSubjects(subjectEntities)
        val insertedSubjects = db.subjectDao().getAllSubjects()
        val insertedLessons = db.lessonDao().getAllLessons()
        Assert.assertEquals(subjectEntities.size, insertedSubjects.size)
        Assert.assertEquals(
            subjectEntities.flatMap { it.chapters.flatMap { chapter -> chapter.lessons } }.size,
            insertedLessons.size
        )
        db.subjectDao().deleteAll()
        val insertedSubjectsAfterDeletion = db.subjectDao().getAllSubjects()
        Assert.assertEquals(insertedSubjectsAfterDeletion.size, 0)
        val lessonsAfterDeletion = db.lessonDao().getAllLessons()
        Assert.assertEquals(lessonsAfterDeletion.size, 0)
    }
}