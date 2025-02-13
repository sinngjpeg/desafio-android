package com.picpay.desafio.android.data.repository

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.common.ApiException
import com.picpay.desafio.android.common.NetworkException
import com.picpay.desafio.android.common.TestCoroutinesDispatchers
import com.picpay.desafio.android.domain.local.UserDAO
import com.picpay.desafio.android.domain.model.MockUsers
import com.picpay.desafio.android.domain.model.UserEntity
import com.picpay.desafio.android.framework.network.PicPayService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException

@ExperimentalCoroutinesApi
class UserRepositoryImplTest {

    private val userDAO: UserDAO = mock()
    private val service: PicPayService = mock()
    private val testDispatcher = TestCoroutinesDispatchers()
    private lateinit var repository: UserRepositoryImpl

    @Before
    fun setup() {
        repository = UserRepositoryImpl(service, userDAO, testDispatcher)
    }

    @Test
    fun `getUsers should return cached users when cache is not empty`() = runTest {
        val cachedUsers = listOf(
            UserEntity(
                1,
                "Sandrine Spinka",
                "Tod86",
                "https://randomuser.me/api/portraits/men/1.jpg"
            ).toDomainModel()
        )

        whenever(userDAO.getAllUsers()).thenReturn(
            cachedUsers.map {
                UserEntity.fromDomainModel(it)
            }
        )

        val result = repository.getUsers()
        assertEquals(cachedUsers, result)
        verify(service, never()).getUsers()
    }

    @Test
    fun `getUsers should fetch from API and cache when cache is empty`() = runTest {
        val apiUsers = MockUsers.users
        
        whenever(userDAO.getAllUsers()).thenReturn(emptyList())
        whenever(service.getUsers()).thenReturn(apiUsers)

        val result = repository.getUsers()

        assertEquals(apiUsers, result)
        verify(userDAO).insertUsers(
            apiUsers.map {
                UserEntity.fromDomainModel(it)
            }
        )
    }

    @Test
    fun `getUsers should throw NetworkException on IOException`() = runTest {
        whenever(userDAO.getAllUsers()).thenReturn(emptyList())
        doAnswer { throw IOException("Network error") }
            .whenever(service).getUsers()

        try {
            repository.getUsers()
            fail("Expected NetworkException to be thrown")
        } catch (e: NetworkException) {
            assertEquals("Erro de rede ao buscar usuários", e.message)
        }
    }

    @Test(expected = ApiException::class)
    fun `getUsers should throw ApiException on HttpException`() = runTest {
        val httpException: HttpException = mock()

        whenever(userDAO.getAllUsers()).thenReturn(emptyList())
        whenever(service.getUsers()).thenThrow(httpException)

        repository.getUsers()
    }
}

