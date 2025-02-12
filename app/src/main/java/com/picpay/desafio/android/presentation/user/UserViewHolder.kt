package com.picpay.desafio.android.presentation.user

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.databinding.ItemUserBinding
import com.picpay.desafio.android.domain.model.User
import com.squareup.picasso.Picasso

class UserViewHolder(
    private val binding: ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.name.text = user.name
        binding.username.text = user.username
        binding.progressBar.visibility = View.VISIBLE

        Picasso.get()
            .load(user.img)
            .error(com.picpay.desafio.android.R.drawable.ic_round_account_circle)
            .into(
                binding.picture,
                object : com.squareup.picasso.Callback {
                    override fun onSuccess() {
                        binding.progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            )
    }
}
