package com.vb.yelplite.app.ui.splash

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.vb.yelplite.app.MainCoroutineRule
import com.vb.yelplite.app.runBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class SplashViewModelTest {

    lateinit var viewModel: SplashViewModel

    @get:Rule
    var coroutinesTestRule = MainCoroutineRule()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `splashAction should be initialized to NOTHING`() = coroutinesTestRule.runBlocking {
        viewModel = SplashViewModel { false }

        assertEquals(SplashAction.NOTHING, viewModel.splashAction.value)
    }

    @Test
    fun `when permission is false, splashAction should change to REQUEST_PERMISSION after 3 seconds`() =
        coroutinesTestRule.runBlocking {
            viewModel = SplashViewModel { false }
            viewModel.create()
            coroutinesTestRule.testDispatcher.advanceTimeBy(3000)

            assertEquals(SplashAction.REQUEST_PERMISSION, viewModel.splashAction.value)
        }

    @Test
    fun `when permission is true, splashAction should change to FINISH after 3 seconds`() =
        coroutinesTestRule.runBlocking {
            viewModel = SplashViewModel { true }
            viewModel.create()
            coroutinesTestRule.testDispatcher.advanceTimeBy(3000)

            assertEquals(SplashAction.FINISH, viewModel.splashAction.value)
        }

    @Test
    fun `when onPermissionsGranted is called, splashAction should change to FINISH`() =
        coroutinesTestRule.runBlocking {
            viewModel = SplashViewModel { true }
            viewModel.onPermissionsGranted()
            assertEquals(SplashAction.FINISH, viewModel.splashAction.value)
        }

    @Test
    fun `when onPermissionsDenied is called, splashAction should change to REINFORCE_PERMISSION after 200ms`() =
        coroutinesTestRule.runBlocking {
            viewModel = SplashViewModel { true }
            viewModel.onPermissionsDenied()
            coroutinesTestRule.testDispatcher.advanceTimeBy(200)
            assertEquals(SplashAction.REINFORCE_PERMISSION, viewModel.splashAction.value)
        }
}