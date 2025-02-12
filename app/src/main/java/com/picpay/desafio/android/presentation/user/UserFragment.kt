package com.picpay.desafio.android.presentation.user

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.FragmentUserBinding
import com.picpay.desafio.android.domain.model.ScreenState
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private val adapter by lazy { UserAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentUserBinding.inflate(
        inflater,
        container,
        false
    ).apply {
        _binding = this
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUsersAdapter()
        observeUsersViewModel()
        setScreenState(ScreenState.LOADING)
        fetchUsers()
    }

    private fun initUsersAdapter() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeUsersViewModel() {
        // Observa a lista de usuários.
        viewModel.users.observe(viewLifecycleOwner) { userList ->
            if (userList.isNotEmpty()) {
                adapter.submitList(userList)
                setScreenState(ScreenState.SUCCESS) // Exibe a lista de usuários.
            } else {
                setScreenState(ScreenState.ERROR) // Exibe a tela de erro se a lista estiver vazia.
            }
        }

        // Observa mensagens de erro.
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
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
