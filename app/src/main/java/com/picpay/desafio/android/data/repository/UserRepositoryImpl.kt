package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.ApiException
import com.picpay.desafio.android.common.GeneralException
import com.picpay.desafio.android.common.NetworkException
import com.picpay.desafio.android.domain.local.UserDAO
import com.picpay.desafio.android.domain.model.UserEntity
import com.picpay.desafio.android.framework.network.PicPayService
import com.picpay.desafio.core.data.repository.UserRepository
import com.picpay.desafio.core.usecase.base.CoroutinesDispatchers
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

    override suspend fun getUsers(): List<com.picpay.desafio.core.domain.model.User> {
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
                throw NetworkException(ErrorMessages.NETWORK_ERROR, e)
            } catch (e: HttpException) {
                throw ApiException(ErrorMessages.HTTP_ERROR, e)
            } catch (e: Exception) {
                throw GeneralException(ErrorMessages.GENERAL_ERROR, e)
            }
        }
    }

    companion object ErrorMessages {
        const val NETWORK_ERROR = "Erro de rede ao buscar usuários"
        const val HTTP_ERROR = "Erro HTTP ao buscar usuários"
        const val GENERAL_ERROR = "Erro inesperado ao buscar usuários"
    }
}

