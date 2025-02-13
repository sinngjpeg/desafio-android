package com.picpay.desafio.android.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.ScreenState
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.usecase.GetUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@Suppress("TooGenericExceptionCaught")
class UserViewModel @Inject constructor(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> get() = _screenState

    fun fetchUsers() {
        viewModelScope.launch {
            _screenState.postValue(ScreenState.LOADING)

            try {
                val users = getUsersUseCase()
                if (users.isEmpty()) {
                    _screenState.postValue(ScreenState.EMPTY)
                } else {
                    _users.postValue(users)
                    _screenState.postValue(ScreenState.SUCCESS)
                }
            } catch (e: Exception) {
                _error.postValue(e.message ?: "Erro desconhecido")
                _screenState.postValue(ScreenState.ERROR)
            }
        }
    }
}
