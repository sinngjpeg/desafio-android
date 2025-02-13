package com.picpay.desafio.android.com.picpay.desafio.android.common

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingResource

class LiveDataIdlingResource<T>(
    private val liveData: MutableLiveData<T>,
    private val expectedValue: T
) : IdlingResource {

    @Volatile
    private var isIdle = false

    private var callback: IdlingResource.ResourceCallback? = null

    private val observer = Observer<T> { value ->
        if (value == expectedValue) {
            isIdle = true
            callback?.onTransitionToIdle()
        }
    }

    override fun getName(): String = "LiveDataIdlingResource"

    override fun isIdleNow(): Boolean {
        if (liveData.value == expectedValue) {
            isIdle = true
        }
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
        liveData.observeForever(observer)
    }

    fun unregister() {
        liveData.removeObserver(observer)
    }
}