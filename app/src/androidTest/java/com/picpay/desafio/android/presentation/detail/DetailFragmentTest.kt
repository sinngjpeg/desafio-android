package com.picpay.desafio.android.presentation.detail

import com.picpay.desafio.android.com.picpay.desafio.android.model.MockUsers
import dagger.hilt.android.testing.HiltAndroidRule
import okhttp3.mockwebserver.MockWebServer
import org.junit.Rule


class DetailFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockUsers = MockUsers.users
    private lateinit var server: MockWebServer


}