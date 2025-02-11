package com.picpay.desafio.android.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.databinding.ListItemUserBinding
import com.picpay.desafio.android.domain.model.User
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class UserListItemViewHolder(
    itemUserBinding: ListItemUserBinding
) : RecyclerView.ViewHolder(itemUserBinding.root) {

    private val name = itemUserBinding.name
    private val username = itemUserBinding.username
    private val picture = itemUserBinding.picture
    private val progressBar = itemUserBinding.progressBar

    fun bind(user: User) {
        name.text = user.name
        username.text = user.username
        progressBar.visibility = View.VISIBLE
        Picasso.get()
            .load(user.img)
            .error(R.drawable.ic_round_account_circle)
            .into(
                picture,
                object : Callback {
                    override fun onSuccess() {
                        progressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        progressBar.visibility = View.GONE
                    }
                }
            )
    }
}
