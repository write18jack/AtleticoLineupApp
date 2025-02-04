package com.whitebeach.presentation.playerSheet

import com.whitebeach.data.model.player.Birth
import com.whitebeach.data.model.player.Paging
import com.whitebeach.data.model.player.Parameters
import com.whitebeach.data.model.player.Player
import com.whitebeach.data.model.player.PlayersResponse
import com.whitebeach.data.model.player.ResponseX
import com.whitebeach.data.remote.FakePlayerApi
import com.whitebeach.data.remote.FakePlayersInfoService
import com.whitebeach.data.remote.PlayerApi
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class RapidApiViewModelTest {

    lateinit var viewModel: RapidApiViewModel
    private lateinit var playerApi: PlayerApi
    private lateinit var fakePlayersInfoService: FakePlayersInfoService
    @Before
    fun setUp() {
        fakePlayersInfoService = FakePlayersInfoService().apply {
            getPlayersImpl = {
                Response.success(
                    PlayersResponse(
                        get ="123456789",
                        parameters = Parameters(
                            team = "",
                            season = "",
                        ),
                        errors = emptyList(),
                        results = 2,
                        paging = Paging(
                            current = 1,
                            total = 1
                        ),
                        response = listOf(
                            ResponseX(
                                player = Player(
                                    id = 1,
                                    name = "name",
                                    firstname = "firstname",
                                    lastname = "lastname",
                                    age = 1,
                                    birth = Birth(
                                        date = "data",
                                        place = "place",
                                        country = "country"
                                    ),
                                    nationality = "nationality",
                                    height = "height",
                                    weight = "weight",
                                    injured = true,
                                    photo = "photo"
                                ),
                                statistics = emptyList()
                            )
                        ),
                    )
                )
            }
        }

        playerApi = FakePlayerApi(fakePlayersInfoService)
        viewModel = RapidApiViewModel(playerApi)
    }


    @Test
    fun aaa() {
        val data: List<ResponseX> = listOf(
            ResponseX(
                player = Player(
                    id = 1,
                    name = "name",
                    firstname = "firstname",
                    lastname = "lastname",
                    age = 1,
                    birth = Birth(
                        date = "data",
                        place = "place",
                        country = "country"
                    ),
                    nationality = "nationality",
                    height = "height",
                    weight = "weight",
                    injured = true,
                    photo = "photo"
                ),
                statistics = emptyList()
            )
        )
        val result = viewModel.playersUiState.value
        assertEquals(data, result)
    }

}