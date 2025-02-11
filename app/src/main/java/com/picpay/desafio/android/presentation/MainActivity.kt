package com.picpay.desafio.android.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.ActivityMainBinding
import com.picpay.desafio.android.domain.model.ScreenState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: UserViewModel by viewModels()
    private val adapter by lazy { UserAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUsersAdapter()
        observeUsersViewModel()
        setScreenState(ScreenState.LOADING)
        fetchUsers()
    }


    private fun initUsersAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }

    private fun observeUsersViewModel() {
        // Observa a lista de usuários.
        viewModel.users.observe(this) { userList ->
            if (userList.isNotEmpty()) {
                adapter.submitList(userList)
                setScreenState(ScreenState.SUCCESS) // Exibe a lista de usuários.
            } else {
                setScreenState(ScreenState.ERROR) // Exibe a tela de erro se a lista estiver vazia.
            }
        }

        // Observa mensagens de erro.
        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
            setScreenState(ScreenState.ERROR) // Exibe a tela de erro em caso de falha.
        }
    }

    private fun setScreenState(state: ScreenState) {
        when (state) {
            ScreenState.LOADING -> {
                Log.d("MainActivity", "Estado: LOADING")
                setShimmerVisibility(true)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_LOADING
            }

            ScreenState.SUCCESS -> {
                Log.d("MainActivity", "Estado: SUCCESS")
                setShimmerVisibility(false)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_SUCCESS
            }

            ScreenState.ERROR -> {
                Log.d("MainActivity", "Estado: ERROR")
                setShimmerVisibility(false)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_ERROR

                // Configura ação do botão "Retry" na tela de erro.
                binding.includeViewErrorState.buttonRetry.setOnClickListener {
                    setScreenState(ScreenState.LOADING)
                    viewModel.fetchUsers()
                }
            }
        }
    }

    private fun setShimmerVisibility(visibility: Boolean) {
        binding.includeViewUserLoadingState.shimmerUsers.run {
            isVisible = visibility
            if (visibility) startShimmer() else stopShimmer()
        }
    }

    private fun fetchUsers() {
        viewModel.fetchUsers()
    }

    companion object {
        private const val FLIPPER_CHILD_LOADING = 0
        private const val FLIPPER_CHILD_SUCCESS = 1
        private const val FLIPPER_CHILD_ERROR = 2
    }
}

