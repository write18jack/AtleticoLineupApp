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
class PlayersRemoteDataSourceTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var remoteDataSource: PlayersRemoteDataSource

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

        remoteDataSource = PlayersRemoteDataSource(
            api = api,
        )
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getPlayers returns players from api`() = runTest {
        val responseBody = """
            [
              {
                "id": 1,
                "name": "Jan Oblak",
                "shirtNumber": 13,
                "position": "GOALKEEPER",
                "nationality": "Slovenia"
              },
              {
                "id": 2,
                "name": "Antoine Griezmann",
                "shirtNumber": 7,
                "position": "FORWARD",
                "nationality": "France"
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

        val result = remoteDataSource.getPlayers()

        assertEquals(
            2,
            result.size,
        )

        assertEquals(
            1,
            result[0].id,
        )

        assertEquals(
            "Jan Oblak",
            result[0].name,
        )

        assertEquals(
            13,
            result[0].shirtNumber,
        )

        assertEquals(
            "GOALKEEPER",
            result[0].position,
        )

        assertEquals(
            "Slovenia",
            result[0].nationality,
        )
    }

    // Retrofitが正しいEndpointを叩いたか確認
    @Test
    fun `getPlayers requests players endpoint`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("[]")
                .addHeader(
                    "Content-Type",
                    "application/json",
                ),
        )

        remoteDataSource.getPlayers()

        val request = mockWebServer.takeRequest()

        assertEquals(
            "/players",
            request.path,
        )

        assertEquals(
            "GET",
            request.method,
        )
    }

    @Test(expected = HttpException::class)
    fun `getPlayers throws HttpException when server returns 500`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(500),
        )

        remoteDataSource.getPlayers()
    }


}
