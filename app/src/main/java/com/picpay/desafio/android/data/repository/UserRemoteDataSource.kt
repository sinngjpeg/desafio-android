package com.picpay.desafio.android.data.repository

interface UserRemoteDataSource<T> {
    suspend fun fetchUsers(): List<T>
}
