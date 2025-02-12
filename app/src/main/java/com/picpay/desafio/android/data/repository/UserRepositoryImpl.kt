package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.CoroutinesDispatchers
import com.picpay.desafio.android.domain.local.UserDAO
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.model.UserEntity
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.framework.network.PicPayService
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@Suppress("TooGenericExceptionCaught")
class UserRepositoryImpl @Inject constructor(
    private val service: PicPayService,
    private val userDAO: UserDAO,
    private val dispatcher: CoroutinesDispatchers
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return withContext(dispatcher.io()) {
            val cachedUsers = userDAO.getAllUsers().map { it.toDomainModel() }
            if (cachedUsers.isNotEmpty()) {
                return@withContext cachedUsers
            }
            try {
                // Caso não haja usuários no cache, chama a API
                val usersFromApi = service.getUsers()
                // Armazenar os usuários no banco de dados local
                userDAO.insertUsers(usersFromApi.map { UserEntity.fromDomainModel(it) })
                // Retorna os usuários obtidos pela API
                return@withContext usersFromApi
            } catch (e: IOException) {
                // Lança uma exceção customizada para falhas de rede
                throw NetworkException("Erro de rede ao buscar usuários", e)
            } catch (e: HttpException) {
                // Lança uma exceção customizada para falhas HTTP
                throw ApiException("Erro HTTP ao buscar usuários", e)
            } catch (e: Exception) {
                // Lança uma exceção genérica mais específica
                throw GeneralException("Erro inesperado ao buscar usuários", e)
            }
        }
    }
}

class NetworkException(message: String, cause: Throwable) : Exception(message, cause)
class ApiException(message: String, cause: Throwable) : Exception(message, cause)
class GeneralException(message: String, cause: Throwable) : Exception(message, cause)

