package com.picpay.desafio.android.common

class NetworkException(
    message: String,
    cause: Throwable
) : Exception(message, cause)

class ApiException(
    message: String,
    cause: Throwable
) : Exception(message, cause)

class GeneralException(
    message: String,
    cause: Throwable
) : Exception(message, cause)
