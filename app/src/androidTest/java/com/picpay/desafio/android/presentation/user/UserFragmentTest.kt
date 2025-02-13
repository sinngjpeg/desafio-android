package com.picpay.desafio.android.presentation.user

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.picpay.desafio.android.R
import com.picpay.desafio.android.com.picpay.desafio.android.common.LiveDataIdlingResource
import com.picpay.desafio.android.com.picpay.desafio.android.model.MockUsers
import com.picpay.desafio.android.domain.model.ScreenState
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class UserFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private val mockUsers = MockUsers.users
    private lateinit var server: MockWebServer

    private val screenStateLiveData = MutableLiveData<ScreenState>()
    private val usersLiveData = MutableLiveData<List<User>>()
    private val errorLiveData = MutableLiveData<String>()

    @Before
    fun setUp() {
//        server = MockWebServer().apply {
//            start(9090)
//        }
        launchFragmentInHiltContainer<UserFragment>()
    }

    @Test
    fun shouldShowTitle_whenStateIsSuccess() {
        screenStateLiveData.postValue(ScreenState.SUCCESS)
        onView(withId(R.id.title))
            .check(matches(isDisplayed()))
        onView(withId(R.id.title))
            .check(matches(withText("Contatos")))
        onView(withId(R.id.title))
            .check(matches(isDisplayed()))
    }

    @Test
    fun shouldShowLoadingIndicator_whenStateIsLoading() {
        val idlingResource = LiveDataIdlingResource(screenStateLiveData, ScreenState.LOADING)
        IdlingRegistry.getInstance().register(idlingResource)
        screenStateLiveData.postValue(ScreenState.LOADING)

        onView(withId(R.id.include_view_user_loading_state))
            .check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @After
    fun tearDown() {
        // Limpa os LiveData após os testes para evitar vazamentos de memória
        screenStateLiveData.postValue(null)
        usersLiveData.postValue(null)
        errorLiveData.postValue(null)
    }
}