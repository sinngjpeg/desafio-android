package com.picpay.desafio.android.domain.model

object MockUsers {
    val users = listOf(
        com.picpay.desafio.core.domain.model.User(
            id = 1,
            name = "Sandrine Spinka",
            username = "Tod86",
            img = "https://randomuser.me/api/portraits/men/1.jpg"
        ),
        com.picpay.desafio.core.domain.model.User(
            id = 2,
            name = "Carli Carroll",
            username = "Constantin_Sawayn",
            img = "https://randomuser.me/api/portraits/men/2.jpg"
        )
    )

    val emptyUsers = emptyList<com.picpay.desafio.core.domain.model.User>()
}
