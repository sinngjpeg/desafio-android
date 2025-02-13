package com.picpay.desafio.android.com.picpay.desafio.android.model

import com.picpay.desafio.android.domain.model.User

object MockUsers {
    val users = listOf(
        User(
            id = 1,
            name = "Sandrine Spinka",
            username = "Tod86",
            img = "https://randomuser.me/api/portraits/men/1.jpg"
        ),
        User(
            id = 2,
            name = "Carli Carroll",
            username = "Constantin_Sawayn",
            img = "https://randomuser.me/api/portraits/men/2.jpg"
        )
    )

    val emptyUsers = emptyList<User>()
}
