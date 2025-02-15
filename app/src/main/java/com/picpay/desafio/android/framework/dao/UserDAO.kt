package com.picpay.desafio.android.framework.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.picpay.desafio.android.framework.entity.UserEntity

@Dao
interface UserDAO {
    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<UserEntity>

    @Query("SELECT * FROM users WHERE name LIKE '%' || :query || '%' OR username LIKE '%' || :query || '%'")
    suspend fun searchUsers(query: String): List<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)
}
