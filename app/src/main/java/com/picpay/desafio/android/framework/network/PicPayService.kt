package com.picpay.desafio.android.framework.network

import com.picpay.desafio.core.domain.model.User
import retrofit2.http.GET

interface PicPayService {
    @GET("users")
    suspend fun getUsers(): List<User>
}
