package com.picpay.desafio.android.presentation.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.picpay.desafio.android.databinding.FragmentUserBinding
import com.picpay.desafio.android.domain.model.ScreenState
import com.picpay.desafio.android.domain.model.User
import com.picpay.desafio.android.presentation.detail.DetailViewArg
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding: FragmentUserBinding get() = _binding!!

    private val viewModel: UserViewModel by viewModels()
    private val adapter by lazy {
        UserAdapter { user ->
            navigateToDetail(user)
        }
    }

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
        fetchUsers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUsersAdapter() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter
    }

    private fun observeUsersViewModel() {
        viewModel.screenState.observe(viewLifecycleOwner) { state ->
            setScreenState(state)
        }

        viewModel.users.observe(viewLifecycleOwner) { userList ->
            adapter.submitList(userList)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToDetail(user: User) {
        val action = UserFragmentDirections.actionUserFragmentToDetailFragment(
            DetailViewArg(
                img = user.img,
                name = user.name,
                id = user.id,
                username = user.username
            )
        )
        findNavController().navigate(action)
    }

    private fun setScreenState(state: ScreenState) {
        when (state) {
            ScreenState.LOADING -> {
                setShimmerVisibility(true)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_LOADING
            }

            ScreenState.SUCCESS -> {
                setShimmerVisibility(false)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_SUCCESS
            }

            ScreenState.EMPTY -> {
                setShimmerVisibility(false)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_ERROR
            }

            ScreenState.ERROR -> {
                setShimmerVisibility(false)
                binding.viewFlipper.displayedChild = FLIPPER_CHILD_ERROR
                binding.includeViewErrorState.buttonRetry.setOnClickListener {
                    fetchUsers()
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
