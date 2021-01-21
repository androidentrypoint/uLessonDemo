package com.example.ulessondemo.repository

import com.example.ulessondemo.network.NetworkProcessorImpl
import com.example.ulessondemo.util.*
import com.example.ulessondemo.util.mapper.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class RepositoryTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    private lateinit var subjectRelationMapper: SubjectRelationMapper
    private lateinit var lessonRelationMapper: LessonRelationMapper

    private lateinit var repository: Repository

    @Before
    fun setUp() {
        subjectRelationMapper = SubjectRelationMapper(
            SubjectMapper(), ChapterRelationMapper(ChapterMapper(), LessonMapper())
        )
        lessonRelationMapper =
            LessonRelationMapper(SubjectMapper(), ChapterMapper(), LessonMapper())
        repository = RepositoryImpl(
            UServiceFake(),
            SubjectDaoFake(),
            LessonDaoFake(),
            coroutinesTestRule.testDispatcherProvider,
            NetworkProcessorImpl(coroutinesTestRule.testDispatcherProvider),
            subjectRelationMapper,
            lessonRelationMapper,
            SubjectDTOMapper(),
            LessonMapper()
        )

    }

    @After
    fun tearDown() {
    }

    @Test
    fun `verify loading items were fetched then success items`() =
        coroutinesTestRule.runBlockingTest {
            val response = repository.getHomeItems()
            val items = response.take(2).toList()
            assertThat(items[0], CoreMatchers.instanceOf(NetworkStatus.Loading::class.java))
            assertThat(items[1], CoreMatchers.instanceOf(NetworkStatus.Success::class.java))
        }
}