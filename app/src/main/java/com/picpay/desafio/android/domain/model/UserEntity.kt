package com.picpay.desafio.android.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val img: String
) {
    fun toDomainModel() = com.picpay.desafio.core.domain.model.User(
        id = id,
        name = name,
        username = username,
        img = img
    )

    companion object {
        fun fromDomainModel(user: com.picpay.desafio.core.domain.model.User) = UserEntity(
            id = user.id,
            name = user.name,
            username = user.username,
            img = user.img
        )
    }
}
