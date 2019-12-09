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
    fun `aa`() = coroutinesTestRule.runBlocking {
        viewModel = SplashViewModel { false }
        viewModel.create()
        coroutinesTestRule.testDispatcher.advanceTimeBy(3000)

        assertEquals(SplashAction.REQUEST_PERMISSION, viewModel.splashAction.value)
    }

    @Test
    fun onPermissionsGranted() {
    }

    @Test
    fun onPermissionsDenied() {
    }
}