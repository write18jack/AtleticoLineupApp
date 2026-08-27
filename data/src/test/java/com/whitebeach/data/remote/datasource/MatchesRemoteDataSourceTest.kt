package com.whitebeach.data.remote.datasource

import com.whitebeach.data.remote.api.AtleticoApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
                "competition": "LALIGA EA SPORTS",
                "matchDay": "Matchday 1",
                "scheduledDate": "2026-08-19T19:00:00+00:00",
                "kickoffAt": "2026-08-19T19:00:00+00:00",
                "homeTeam": "Atletico Madrid",
                "awayTeam": "Real Madrid",
                "homeTeamImageUrl": "https://example.com/atletico.png",
                "awayTeamImageUrl": "https://example.com/real-madrid.png",
                "venueName": "Riyadh Air Metropolitano",
                "venueCity": "Madrid",
                "status": "UPCOMING",
                "homeScore": null,
                "awayScore": null
              },
              {
                "id": 1002,
                "competition": "LALIGA EA SPORTS",
                "matchDay": "Matchday 2",
                "scheduledDate": "2026-08-27T19:00:00+00:00",
                "kickoffAt": "2026-08-27T19:00:00+00:00",
                "homeTeam": "Barcelona",
                "awayTeam": "Atletico Madrid",
                "homeTeamImageUrl": "https://example.com/barcelona.png",
                "awayTeamImageUrl": "https://example.com/atletico.png",
                "venueName": "Spotify Camp Nou",
                "venueCity": "Barcelona",
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

        val upcoming = result[0]

        assertEquals(
            1001,
            upcoming.id,
        )

        assertEquals(
            "LALIGA EA SPORTS",
            upcoming.competition,
        )

        assertEquals(
            "Matchday 1",
            upcoming.matchDay,
        )

        assertEquals(
            "2026-08-19T19:00:00+00:00",
            upcoming.scheduledDate,
        )

        assertEquals(
            "2026-08-19T19:00:00+00:00",
            upcoming.kickoffAt,
        )

        assertEquals(
            "Atletico Madrid",
            upcoming.homeTeam,
        )

        assertEquals(
            "Real Madrid",
            upcoming.awayTeam,
        )

        assertEquals(
            "https://example.com/atletico.png",
            upcoming.homeTeamImageUrl,
        )

        assertEquals(
            "https://example.com/real-madrid.png",
            upcoming.awayTeamImageUrl,
        )

        assertEquals(
            "Riyadh Air Metropolitano",
            upcoming.venueName,
        )

        assertEquals(
            "Madrid",
            upcoming.venueCity,
        )

        assertEquals(
            "UPCOMING",
            upcoming.status,
        )

        assertNull(
            upcoming.homeScore,
        )

        assertNull(
            upcoming.awayScore,
        )

        val finished = result[1]

        assertEquals(
            "FINISHED",
            finished.status,
        )

        assertEquals(
            1,
            finished.homeScore,
        )

        assertEquals(
            2,
            finished.awayScore,
        )
    }

    @Test
    fun `getMatches parses match with unconfirmed kickoff`() = runTest {
        val responseBody = """
            [
              {
                "id": 2001,
                "competition": "LALIGA EA SPORTS",
                "matchDay": null,
                "scheduledDate": "2026-09-13T00:00:00+00:00",
                "kickoffAt": null,
                "homeTeam": "Atletico Madrid",
                "awayTeam": "Test Club",
                "homeTeamImageUrl": null,
                "awayTeamImageUrl": null,
                "venueName": null,
                "venueCity": null,
                "status": "UPCOMING",
                "homeScore": null,
                "awayScore": null
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
            1,
            result.size,
        )

        val match = result.first()

        assertEquals(
            2001,
            match.id,
        )

        assertEquals(
            "2026-09-13T00:00:00+00:00",
            match.scheduledDate,
        )

        assertNull(
            match.kickoffAt,
        )

        assertNull(
            match.matchDay,
        )

        assertNull(
            match.homeTeamImageUrl,
        )

        assertNull(
            match.awayTeamImageUrl,
        )

        assertNull(
            match.venueName,
        )

        assertNull(
            match.venueCity,
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