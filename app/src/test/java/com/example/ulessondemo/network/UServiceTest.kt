package com.example.ulessondemo.network

import com.example.ulessondemo.util.CoroutineTestRule
import com.example.ulessondemo.util.Util
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.SocketPolicy
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class UServiceTest {

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()

    lateinit var service: UService
    lateinit var mockWebServer: MockWebServer

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UService::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun `verify subject response from server is correctly returned and parsed`() = runBlocking {
        enqueueResponse("sample-subject-response.json")
        val response = service.getSubjects()
        val request = mockWebServer.takeRequest()
        Assert.assertEquals("GET", request.method)
        Assert.assertEquals("/3p/api/content/grade", request.path)
        Assert.assertEquals(response.isSuccessful, true)

        val subjects = response.body()!!.dto!!
        Assert.assertEquals(subjects.size, 5)
        Assert.assertEquals(subjects.first().chapters.size, 1)
        Assert.assertEquals(subjects.first().chapters.first().lessons.size, 1)
        Assert.assertEquals(subjects.first().name, "Mathematics")
        Assert.assertEquals(subjects.first().chapters.first().name, "Exam Test")
        Assert.assertEquals(subjects.first().chapters.first().lessons.first().name, "dsfjdfkj")
    }

    private fun enqueueResponse(
        fileName: String,
        headers: Map<String, String> = emptyMap(),
        socketPolicy: SocketPolicy = SocketPolicy.KEEP_OPEN
    ) {
        val mockResponse = MockResponse().apply {
            this.socketPolicy = socketPolicy
            for ((key, value) in headers) {
                addHeader(key, value)
            }
        }

        mockWebServer.enqueue(
            mockResponse
                .setBody(Util.getJsonStringFromFile(fileName))
        )
    }
}