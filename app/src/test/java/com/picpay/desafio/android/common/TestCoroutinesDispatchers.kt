package com.picpay.desafio.android.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher

@ExperimentalCoroutinesApi
class TestCoroutinesDispatchers(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : CoroutinesDispatchers {
    override fun main(): CoroutineDispatcher = testDispatcher
    override fun default(): CoroutineDispatcher = testDispatcher
    override fun io(): CoroutineDispatcher = testDispatcher
    override fun unconfined(): CoroutineDispatcher = testDispatcher
}
