package com.picpay.desafio.core.data.repository

interface UserRepository {
    suspend fun getUsers(): List<com.picpay.desafio.core.domain.model.User>
}
