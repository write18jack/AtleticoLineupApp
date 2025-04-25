package com.whitebeach.presentation.playerSheet

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.whitebeach.data.model.player.ResponseX
import com.whitebeach.data.repository.PlayersRepository
import com.whitebeach.presentation.fake.FakePlayersInfoService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

@ExperimentalCoroutinesApi
class RapidApiViewModelTest {

    private lateinit var viewModel: RapidApiViewModel
    private lateinit var playersRepository: PlayersRepository
    private lateinit var repository: PlayersRepository

    @get:Rule
    val testInstantTaskExecutorRules: TestRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        playersRepository = PlayersRepository(FakePlayersInfoService())
        repository = mockk<PlayersRepository>()
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun viewmodel_getPlayers_verifyPlayers() = runTest(UnconfinedTestDispatcher()) {

        // ViewModelのインスタンス化（initブロックが実行される）
        viewModel = RapidApiViewModel(playersRepository)
        val data: List<ResponseX> = playersRepository.getPlayers().body()!!.response
        testDispatcher.scheduler.advanceUntilIdle()
        val result = viewModel.playersUiState

        // データ取得状態を検証
        assertEquals(PlayersUiState.Success(data), result)
    }

    @Test
    fun viewmodel_getPlayers_error() = runTest(UnconfinedTestDispatcher()) {
        // モックのエラーレスポンスを設定
        val errorMessage = "error message"

        // suspend関数の場合 coEvery
        coEvery { repository.getPlayers() } throws RuntimeException(errorMessage)

        // ViewModelのインスタンス化（initブロックが実行される）
        val viewModel = RapidApiViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        // エラー状態を検証
        assertEquals(PlayersUiState.Error(errorMessage), viewModel.playersUiState)
    }

}
