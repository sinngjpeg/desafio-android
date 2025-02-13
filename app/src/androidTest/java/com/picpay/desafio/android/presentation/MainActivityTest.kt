package com.picpay.desafio.android.presentation

import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.picpay.desafio.android.R
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivityRecreation() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            // Verificar estado inicial ou configuração inicial
            assertNotNull(activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment))
        }

        // Recriar a atividade
        scenario.recreate()

        scenario.onActivity { activity ->
            // Verificar novamente após recriação
            assertNotNull(activity.supportFragmentManager.findFragmentById(R.id.nav_host_fragment))
        }
    }
}