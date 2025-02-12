package com.picpay.desafio.android.presentation.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.model.ScreenState
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
@Suppress("TooGenericExceptionCaught")
class UserViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    private val _screenState = MutableLiveData<ScreenState>()
    val screenState: LiveData<ScreenState> get() = _screenState

    fun fetchUsers() {
        viewModelScope.launch {
            // Atualiza o estado da tela para LOADING
            _screenState.postValue(ScreenState.LOADING)

            try {
                // Realiza a operação de entrada/saída em uma thread de background (Dispatchers.IO)
                val users = withContext(Dispatchers.IO) {
                    repository.getUsers()
                }

                // Atualiza os estados com base nos dados retornados
                if (users.isEmpty()) {
                    _screenState.postValue(ScreenState.EMPTY)
                } else {
                    _users.postValue(users)
                    _screenState.postValue(ScreenState.SUCCESS)
                }
            } catch (e: Exception) {
                // Trata erros e atualiza os estados correspondentes
                _error.postValue(e.message ?: "Erro desconhecido")
                _screenState.postValue(ScreenState.ERROR)
            }
        }
    }
}
