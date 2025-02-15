package com.picpay.desafio.core.usecase


import com.picpay.desafio.core.data.repository.UserRepository
import com.picpay.desafio.core.domain.model.User
import com.picpay.desafio.core.usecase.base.CoroutinesDispatchers
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
