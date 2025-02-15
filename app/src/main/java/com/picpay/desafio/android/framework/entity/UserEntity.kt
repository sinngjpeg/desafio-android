package com.picpay.desafio.android.framework.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.picpay.desafio.core.domain.model.User

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val img: String
) {
    fun toDomainModel() = User(
        id = id,
        name = name,
        username = username,
        img = img
    )

    companion object {
        fun fromDomainModel(user: User) = UserEntity(
            id = user.id,
            name = user.name,
            username = user.username,
            img = user.img
        )
    }
}
