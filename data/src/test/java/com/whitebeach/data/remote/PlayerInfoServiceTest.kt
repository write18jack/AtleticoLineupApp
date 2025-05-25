package com.whitebeach.data.remote

import com.whitebeach.data.repository.PlayersRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PlayerInfoServiceTest {

    private lateinit var playersRepository: PlayersRepository

    @Before
    fun setUp() {
        playersRepository = PlayersRepository(FakePlayersInfoService())
    }

    @Test
    fun data_isNotEmpty() = runTest {
        val players = playersRepository.getPlayers().body()!!.response
        assert(players.isNotEmpty())
    }

    @Test
    fun data_isCorrect() = runTest {
        val players = playersRepository.getPlayers().body()!!.response
        assert(players[0].player.name == "Sa\u00fal")
    }

    @Test
    fun data_isCorrect2() = runTest {
        val players = playersRepository.getPlayers2().body()!!.response
        assert(players[0].player.name == "Sa\u00fal")
    }

    @Test
    fun data_isCorrect3() = runTest {
        val players = playersRepository.getPlayers3().body()!!.response
        assert(players[0].player.name == "Sa\u00fal")
    }

}