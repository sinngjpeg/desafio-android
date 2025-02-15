package com.picpay.desafio.android.presentation.user

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.picpay.desafio.android.domain.model.MockUsers
import com.picpay.desafio.core.usecase.GetUsersUseCase
import com.picpay.desafio.core.usecase.base.ScreenState
import io.mockk.called
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: UserViewModel
    private val getUsersUseCase: GetUsersUseCase = mockk()
    private val usersObserver: Observer<List<com.picpay.desafio.core.domain.model.User>> = mockk(relaxed = true)
    private val errorObserver: Observer<String> = mockk(relaxed = true)
    private val screenStateObserver: Observer<ScreenState> = mockk(relaxed = true)
    private val mockUsers = MockUsers.users

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = UserViewModel(this.getUsersUseCase)
        viewModel.users.observeForever(usersObserver)
        viewModel.error.observeForever(errorObserver)
        viewModel.screenState.observeForever(screenStateObserver)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        viewModel.users.removeObserver(usersObserver)
        viewModel.error.removeObserver(errorObserver)
        viewModel.screenState.removeObserver(screenStateObserver)
    }

    @Test
    fun `fetchUsers success returns users`() = runTest {
        // Arrange
        coEvery { getUsersUseCase() } returns mockUsers

        // Act
        viewModel.fetchUsers()
        advanceUntilIdle()

        // Assert
        verify { screenStateObserver.onChanged(ScreenState.LOADING) }
        verify { usersObserver.onChanged(mockUsers) }
        verify { screenStateObserver.onChanged(ScreenState.SUCCESS) }
        verify { errorObserver wasNot called }
    }

    @Test
    fun `fetchUsers success returns empty list`() = runTest {
        // Arrange
        coEvery { getUsersUseCase() } returns emptyList()

        // Act
        viewModel.fetchUsers()
        advanceUntilIdle()

        // Assert
        verify { screenStateObserver.onChanged(ScreenState.LOADING) }
        verify { screenStateObserver.onChanged(ScreenState.EMPTY) }
        verify { errorObserver wasNot called }
    }

    @Test
    fun `fetchUsers error returns error message`() = runTest {
        // Arrange
        val errorMessage = "Network Error"
        coEvery { getUsersUseCase() } throws Exception(errorMessage)

        // Act
        viewModel.fetchUsers()
        advanceUntilIdle()

        // Assert
        verify { screenStateObserver.onChanged(ScreenState.LOADING) }
        verify { errorObserver.onChanged(errorMessage) }
        verify { screenStateObserver.onChanged(ScreenState.ERROR) }
    }


    @Test
    fun `fetchUsers - error returns unknown error message`() = runTest {
        // Arrange
        coEvery { getUsersUseCase() } throws Exception()

        // Act
        viewModel.fetchUsers()
        advanceUntilIdle()

        // Assert
        verify { screenStateObserver.onChanged(ScreenState.LOADING) }
        verify { errorObserver.onChanged("Erro desconhecido") }
        verify { screenStateObserver.onChanged(ScreenState.ERROR) }
    }
}
