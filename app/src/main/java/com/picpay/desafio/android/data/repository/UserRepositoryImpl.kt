package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.ApiException
import com.picpay.desafio.android.common.CoroutinesDispatchers
import com.picpay.desafio.android.common.GeneralException
import com.picpay.desafio.android.common.NetworkException
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
                val usersFromApi = service.getUsers()
                userDAO.insertUsers(usersFromApi.map { UserEntity.fromDomainModel(it) })
                return@withContext usersFromApi
            } catch (e: IOException) {
                throw NetworkException("Erro de rede ao buscar usuários", e)
            } catch (e: HttpException) {
                throw ApiException("Erro HTTP ao buscar usuários", e)
            } catch (e: Exception) {
                throw GeneralException("Erro inesperado ao buscar usuários", e)
            }
        }
    }
}

