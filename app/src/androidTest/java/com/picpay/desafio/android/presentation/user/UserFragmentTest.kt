package com.picpay.desafio.android.presentation.user

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withChild
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import com.picpay.desafio.android.R
import com.picpay.desafio.android.presentation.MainActivity
import com.picpay.desafio.android.presentation.detail.DetailViewArg
import dagger.hilt.android.testing.HiltAndroidRule
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserFragmentTest{


    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val activityRule = ActivityTestRule(MainActivity::class.java)

    private lateinit var mockNavController: NavController

    @Before
    fun setup() {
        hiltRule.inject()
        mockNavController = mock(NavController::class.java)
    }

    @Test
    fun testRecyclerViewDisplaysUsers() {
        // Certificar-se de que o RecyclerView está visível e exibe itens.
        onView(withId(R.id.recyclerView))
            .check(matches(isDisplayed()))

        // Simular clique no primeiro item da lista.
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(0, click()))
    }

    @Test
    fun testNavigateToDetailFragment() {
        // Configurar NavController simulado.
        val scenario = launchFragmentInHiltContainer<UserFragment> {
            Navigation.setViewNavController(requireView(), mockNavController)
        }

        // Simular clique no primeiro item da lista.
        onView(withId(R.id.recyclerView))
            .perform(RecyclerViewActions.actionOnItemAtPosition<UserAdapter.UserViewHolder>(0, click()))

        // Verificar se a navegação foi chamada corretamente.
        verify(mockNavController).navigate(
            UserFragmentDirections.actionUserFragmentToDetailFragment(
                DetailViewArg("img_url", "name", 1, "username")
            )
        )
    }

    @Test
    fun testScreenStateLoading() {
        // Simular estado de carregamento.
        onView(withId(R.id.viewFlipper))
            .check(matches(isDisplayed()))
            .check(matches(withChild(withId(R.id.loading_view))))
    }

    @Test
    fun testScreenStateError() {
        // Simular estado de erro.
        onView(withId(R.id.viewFlipper))
            .check(matches(isDisplayed()))
            .check(matches(withChild(withId(R.id.error_view))))
    }
}
