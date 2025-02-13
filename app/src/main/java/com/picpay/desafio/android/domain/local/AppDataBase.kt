package com.picpay.desafio.android.domain.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.picpay.desafio.android.domain.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDAO
}
