package com.whitebeach.data.remote.datasource

import com.whitebeach.data.remote.api.AtleticoApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * RemoteDataSourceTest
 * → Retrofit / JSON / HTTP
 */
class MatchesRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteDataSource: MatchesRemoteDataSource

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                GsonConverterFactory.create(),
            )
            .build()

        val api = retrofit.create(
            AtleticoApi::class.java,
        )

        remoteDataSource = MatchesRemoteDataSource(
            api = api,
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getMatches returns matches from api`() = runTest {
        val responseBody = """
            [
              {
                "id": 1001,
                "competition": "LaLiga",
                "homeTeam": "Atletico Madrid",
                "awayTeam": "Real Madrid",
                "date": "2026-09-20",
                "time": "21:00",
                "status": "UPCOMING",
                "homeScore": null,
                "awayScore": null
              },
              {
                "id": 1002,
                "competition": "LaLiga",
                "homeTeam": "Barcelona",
                "awayTeam": "Atletico Madrid",
                "date": "2026-09-27",
                "time": "20:00",
                "status": "FINISHED",
                "homeScore": 1,
                "awayScore": 2
              }
            ]
        """.trimIndent()

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(responseBody)
                .addHeader(
                    "Content-Type",
                    "application/json",
                ),
        )

        val result = remoteDataSource.getMatches()

        assertEquals(
            2,
            result.size,
        )

        assertEquals(
            1001,
            result[0].id,
        )

        assertEquals(
            "LaLiga",
            result[0].competition,
        )

        assertEquals(
            "Atletico Madrid",
            result[0].homeTeam,
        )

        assertEquals(
            "Real Madrid",
            result[0].awayTeam,
        )

        assertEquals(
            "UPCOMING",
            result[0].status,
        )
    }

    @Test
    fun `getMatches requests matches endpoint`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("[]")
                .addHeader(
                    "Content-Type",
                    "application/json",
                ),
        )

        remoteDataSource.getMatches()

        val request = mockWebServer.takeRequest()

        assertEquals(
            "/matches",
            request.path,
        )

        assertEquals(
            "GET",
            request.method,
        )
    }

    @Test(expected = HttpException::class)
    fun `getMatches throws HttpException when server returns 500`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500),
        )

        remoteDataSource.getMatches()
    }
}