package com.picpay.desafio.android.data.repository

import com.picpay.desafio.android.common.CoroutinesDispatchers
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import com.picpay.desafio.android.framework.network.PicPayService
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val service: PicPayService,
    private val dispatcher: CoroutinesDispatchers
) : UserRepository {

    override suspend fun getUsers(): List<User> {
        return withContext(dispatcher.io()) {
            service.getUsers()
        }
    }
}
