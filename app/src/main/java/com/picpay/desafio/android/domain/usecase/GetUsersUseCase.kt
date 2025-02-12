package com.picpay.desafio.android.domain.usecase

import com.picpay.desafio.android.common.CoroutinesDispatchers
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val repository: UserRepository,
    private val dispatcher: CoroutinesDispatchers
) {
    suspend operator fun invoke(): List<User> {
        return withContext(dispatcher.io()) {
            repository.getUsers()
        }
    }
}
