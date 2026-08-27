package com.whitebeach.presentation.matches

import com.whitebeach.domain.model.Match
import com.whitebeach.domain.model.MatchStatus
import com.whitebeach.presentation.matches.list.toUiModel
import com.whitebeach.presentation.matches.model.MatchStatusUi
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.Locale
import java.util.TimeZone

class MatchMapperTest {

    private lateinit var originalTimeZone: TimeZone
    private lateinit var originalLocale: Locale

    @Before
    fun setUp() {
        originalTimeZone = TimeZone.getDefault()
        originalLocale = Locale.getDefault()

        TimeZone.setDefault(
            TimeZone.getTimeZone("Asia/Tokyo"),
        )

        Locale.setDefault(Locale.ENGLISH)
    }

    @After
    fun tearDown() {
        TimeZone.setDefault(originalTimeZone)
        Locale.setDefault(originalLocale)
    }

    @Test
    fun `kickoffAt is converted to device local date and time`() {
        val match = createMatch(
            scheduledDate = "2026-08-19T19:00:00+00:00",
            kickoffAt = "2026-08-19T19:00:00+00:00",
        )

        val actual = match.toUiModel()

        assertEquals(
            "Aug 20, 2026",
            actual.dateText,
        )

        assertEquals(
            "04:00",
            actual.timeText,
        )
    }

    @Test
    fun `kickoffAt null uses scheduled date and TBD`() {
        val match = createMatch(
            scheduledDate = "2026-09-13T00:00:00+00:00",
            kickoffAt = null,
        )

        val actual = match.toUiModel()

        assertEquals(
            "Sep 13, 2026",
            actual.dateText,
        )

        assertEquals(
            "TBD",
            actual.timeText,
        )
    }

    @Test
    fun `upcoming status is mapped to UPCOMING`() {
        val match = createMatch(
            status = MatchStatus.UPCOMING,
        )

        val actual = match.toUiModel()

        assertEquals(
            MatchStatusUi.UPCOMING,
            actual.status,
        )
    }

    @Test
    fun `finished status is mapped to FINISHED`() {
        val match = createMatch(
            status = MatchStatus.FINISHED,
            homeScore = 2,
            awayScore = 1,
        )

        val actual = match.toUiModel()

        assertEquals(
            MatchStatusUi.FINISHED,
            actual.status,
        )

        assertEquals(
            2,
            actual.homeScore,
        )

        assertEquals(
            1,
            actual.awayScore,
        )
    }

    @Test
    fun `unknown status is mapped to UNKNOWN`() {
        val match = createMatch(
            status = MatchStatus.UNKNOWN,
        )

        val actual = match.toUiModel()

        assertEquals(
            MatchStatusUi.UNKNOWN,
            actual.status,
        )
    }

    @Test
    fun `match detail fields are mapped correctly`() {
        val match = createMatch(
            competition = "LALIGA EA SPORTS",
            matchday = "Matchday 1",
            venueName = "Riyadh Air Metropolitano",
            venueCity = "Madrid",
        )

        val actual = match.toUiModel()

        assertEquals(
            "LALIGA EA SPORTS",
            actual.competitionName,
        )

        assertEquals(
            "Matchday 1",
            actual.matchDayText,
        )

        assertEquals(
            "Riyadh Air Metropolitano",
            actual.venueName,
        )

        assertEquals(
            "Madrid",
            actual.venueCity,
        )
    }

    @Test
    fun `team information is mapped correctly`() {
        val match = createMatch(
            homeTeam = "Atlético de Madrid",
            awayTeam = "FC Barcelona",
            homeTeamImageUrl = "https://example.com/atletico.png",
            awayTeamImageUrl = "https://example.com/barcelona.png",
        )

        val actual = match.toUiModel()

        assertEquals(
            "Atlético de Madrid",
            actual.homeTeamName,
        )

        assertEquals(
            "FC Barcelona",
            actual.awayTeamName,
        )

        assertEquals(
            "https://example.com/atletico.png",
            actual.homeTeamImageUrl,
        )

        assertEquals(
            "https://example.com/barcelona.png",
            actual.awayTeamImageUrl,
        )
    }

    private fun createMatch(
        id: Int = 1,
        competition: String = "LALIGA EA SPORTS",
        matchday: String? = "Matchday 1",
        scheduledDate: String = "2026-08-19T19:00:00+00:00",
        kickoffAt: String? = "2026-08-19T19:00:00+00:00",
        homeTeam: String = "Atlético de Madrid",
        awayTeam: String = "Málaga CF",
        homeTeamImageUrl: String? = null,
        awayTeamImageUrl: String? = null,
        venueName: String? = "Riyadh Air Metropolitano",
        venueCity: String? = "Madrid",
        status: MatchStatus = MatchStatus.UPCOMING,
        homeScore: Int? = null,
        awayScore: Int? = null,
    ): Match {
        return Match(
            id = id,
            competition = competition,
            matchDay = matchday,
            scheduledDate = scheduledDate,
            kickoffAt = kickoffAt,
            homeTeam = homeTeam,
            awayTeam = awayTeam,
            homeTeamImageUrl = homeTeamImageUrl,
            awayTeamImageUrl = awayTeamImageUrl,
            venueName = venueName,
            venueCity = venueCity,
            status = status,
            homeScore = homeScore,
            awayScore = awayScore,
        )
    }
}